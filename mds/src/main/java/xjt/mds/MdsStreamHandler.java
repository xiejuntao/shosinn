package xjt.mds;

import lombok.extern.slf4j.Slf4j;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MdsStreamHandler {
    private static final int MAXIN = 1024;
    private static final int MAXOUT = 1024;
    private ByteBuffer input = ByteBuffer.allocate(MAXIN);
    private ByteBuffer output = ByteBuffer.allocate(MAXOUT);
    // 缓存每次读取的内容
    private StringBuilder reqCache = new StringBuilder();
    private SocketChannel socketChannel;
    public MdsStreamHandler(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }
    private boolean inputIsComplete(int bytes) throws EOFException {
        if (bytes > 0) {
            input.flip(); // 切换成读取模式
            while (input.hasRemaining()) {
                byte ch = input.get();
                if (ch == 3) { // ctrl+c 关闭连接
                    return true;
                } else if (ch == '\r') { // continue
                } else if (ch == '\n') {
                    // 读取到了 \r\n 读取结束
                    return true;
                } else {
                    reqCache.append((char) ch);
                }
            }
        } else if (bytes == -1) {
            // -1 客户端关闭了连接
            throw new EOFException();
        } else {
        } // bytes == 0 继续读取
        return false;
    }
    private boolean outputIsComplete(int written) {
        if (written <= 0) {
            // 用户只敲了个回车， 断开连接
            return true;
        }
        // 清空旧数据，接着处理后续的请求
        output.clear();
        reqCache.delete(0, reqCache.length());
        return false;
    }
    public void readCompleted(MdsReadStreamCallback callback) throws IOException {
        input.clear(); // 清空接收缓冲区
        int n = socketChannel.read(input);
        if (inputIsComplete(n)) {
            callback.readCompleted(reqCache.toString());
        }
    }
    public void writeCompleted(MdsWriteStreamCallback callback) throws IOException {
        int written = -1;
        output.flip();// 切换到读取模式，判断是否有数据要发送
        if (output.hasRemaining()) {
            written = socketChannel.write(output);
        }
        // 检查连接是否处理完毕，是否断开连接
        if (outputIsComplete(written)) {
            callback.closeSession();
        } else {
            // 把提示发到界面
            socketChannel.write(ByteBuffer.wrap(("\r\n"+MdsConstants.SINGLETIP).getBytes()));
            callback.writeCompleted();
        }
    }

    public void write(String res) {
        byte[] response = res.getBytes(StandardCharsets.UTF_8);
        output.put(response);
    }

    public interface MdsReadStreamCallback {
        public void readCompleted(String req);
    }
    public interface MdsWriteStreamCallback{
        public void closeSession() throws IOException;
        public void writeCompleted();
    }
}
