package xjt.scalableio;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class XHandler implements Runnable{
    private static final int MAXIN = 1024;
    private static final int MAXOUT = 1024;
    SocketChannel socketChannel;
    SelectionKey selectionKey;
    static final int READING=0,SENDING=1,CLOSED = 2;

    ByteBuffer input = ByteBuffer.allocate(MAXIN);
    ByteBuffer output = ByteBuffer.allocate(MAXOUT);

    int state = READING;

    public XHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector,0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();// 唤醒 select() 方法
    }

    public XHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            }
            if (state == SENDING) {
                send();
            }
        }catch (IOException e){
            e.printStackTrace();
            // 关闭连接
            try {
                selectionKey.channel().close();
            } catch (IOException ignore) {
            }
        }

    }

    protected void send() throws IOException {
        int written = -1;
        output.flip();// 切换到读取模式，判断是否有数据要发送
        if (output.hasRemaining()) {
            written = socketChannel.write(output);
        }

        // 检查连接是否处理完毕，是否断开连接
        if (outputIsComplete(written)) {
            selectionKey.channel().close();
        } else {
            // 否则继续读取
            state = READING;
            // 把提示发到界面
            socketChannel.write(ByteBuffer.wrap("\r\nreactor> ".getBytes()));
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }

    protected void read() throws IOException {
        input.clear(); // 清空接收缓冲区
        int n = socketChannel.read(input);
        if(inputIsComplete(n)) {
            process();
            state = SENDING;
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }
    protected void process() throws EOFException {
        if (state == CLOSED) {
            throw new EOFException();
        } else if (state == SENDING) {
            String requestContent = request.toString(); // 请求内容
            byte[] response = requestContent.getBytes(StandardCharsets.UTF_8);
            output.put(response);
        }
    }
    // 缓存每次读取的内容
    StringBuilder request = new StringBuilder();
    protected boolean inputIsComplete(int bytes) throws EOFException {
        if (bytes > 0) {
            input.flip(); // 切换成读取模式
            while (input.hasRemaining()) {
                byte ch = input.get();

                if (ch == 3) { // ctrl+c 关闭连接
                    state = CLOSED;
                    return true;
                } else if (ch == '\r') { // continue
                } else if (ch == '\n') {
                    // 读取到了 \r\n 读取结束
                    state = SENDING;
                    return true;
                } else {
                    request.append((char)ch);
                }
            }
        } else if (bytes == -1) {
            // -1 客户端关闭了连接
            throw new EOFException();
        } else {} // bytes == 0 继续读取
        return false;
    }
    private boolean outputIsComplete(int written) {
        if (written <= 0) {
            // 用户只敲了个回车， 断开连接
            return true;
        }

        // 清空旧数据，接着处理后续的请求
        output.clear();
        request.delete(0, request.length());
        return false;
    }
}
