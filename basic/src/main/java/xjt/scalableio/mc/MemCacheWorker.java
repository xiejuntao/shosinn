package xjt.scalableio.mc;

import lombok.extern.slf4j.Slf4j;
import xjt.concurrent.AdvanceExecutors;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
@Slf4j
public class MemCacheWorker {
    private static final int MAXIN = 1024;
    private static final int MAXOUT = 1024;
    private SocketChannel socketChannel;
    private ByteBuffer input = ByteBuffer.allocate(MAXIN);
    private ByteBuffer output = ByteBuffer.allocate(MAXOUT);
    public MemCacheWorker(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }
    public SocketChannel getSocketChannel(){
        return this.socketChannel;
    }
    public void handle(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isReadable()) {
            input.clear(); // 清空接收缓冲区
            int n = socketChannel.read(input);
            log.info("read`n={}", n);
            if (inputIsComplete(n)) {
                process(selectionKey);
            }
        }
        if (selectionKey.isWritable()) {
            int written = -1;
            output.flip();// 切换到读取模式，判断是否有数据要发送
            if (output.hasRemaining()) {
                written = socketChannel.write(output);
            }
            // 检查连接是否处理完毕，是否断开连接
            if (outputIsComplete(written)) {
                selectionKey.channel().close();
            } else {
                // 把提示发到界面
                socketChannel.write(ByteBuffer.wrap(("\r\n"+MemCacheConstants.TIP).getBytes()));
                selectionKey.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    // 缓存每次读取的内容
    StringBuilder reqCache = new StringBuilder();
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
    private ExecutorService executorService = AdvanceExecutors.newFixedThreadPoolWithQueueSizeByName("worker-thread",10,100);
    private void process(SelectionKey selectionKey) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String requestContent = reqCache.toString();
                String responseContent = requestContent.toUpperCase(); // 请求内容转大写作为输出
                log.info("process`requestContent={}`responseContent={}",requestContent,responseContent);
                byte[] response = responseContent.getBytes(StandardCharsets.UTF_8);
                output.put(response);
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                selectionKey.selector().wakeup();
            }
        });
    }
}
