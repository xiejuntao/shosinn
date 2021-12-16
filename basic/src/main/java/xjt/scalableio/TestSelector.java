package xjt.scalableio;

import lombok.extern.slf4j.Slf4j;
import xjt.concurrent.AdvanceExecutors;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@Slf4j
public class TestSelector {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = AdvanceExecutors.newFixedThreadPoolWithQueueSize(10,1000);
        int port = 11002;
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port)); // 绑定端口
        // 设置成非阻塞模式
        serverSocket.configureBlocking(false);
        Selector selector = Selector.open();
        // 注册到 选择器 并设置处理 socket 连接事件
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            int size = selector.select(); // 阻塞，直到有通道事件就绪
            log.info("main size={}",size);
            if(size>0){
                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> iterator = set.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if(selectionKey.isAcceptable()){
                        SocketChannel socketChannel = serverSocket.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.write(ByteBuffer.wrap("reactor> ".getBytes()));
                        log.info("accept");
                        if(socketChannel!=null) {
                            socketChannel.configureBlocking(false);
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        new Worker().handle(socketChannel);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }
    static class Worker{
        private static final int MAXIN = 1024;
        private static final int MAXOUT = 1024;
        Selector selector;
        SocketChannel socketChannel;
        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);
        public Worker() throws IOException {
            this.selector  = Selector.open();
        }
        public void handle(SocketChannel socketChannel) throws IOException {
            this.socketChannel = socketChannel;
            socketChannel.register(selector, SelectionKey.OP_READ);
            while (true) {
                int size = selector.select();
                log.info("sub size={}",size);
                if (size > 0) {
                    Set<SelectionKey> set = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = set.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        log.info("selectionKey`ops={}`accept={}`writable={}`readable={}"
                                ,selectionKey.interestOps(),selectionKey.isAcceptable(),selectionKey.isWritable(),selectionKey.isReadable());
                        if (selectionKey.isReadable()) {
                            input.clear(); // 清空接收缓冲区
                            int n = socketChannel.read(input);
                            log.info("read`n={}",n);
                            if(inputIsComplete(n)) {
                                process();
                                selectionKey.interestOps(SelectionKey.OP_WRITE);
                            }
                        }
                        if(selectionKey.isWritable()){
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
                                socketChannel.write(ByteBuffer.wrap("\r\nreactor> ".getBytes()));
                                selectionKey.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                    set.clear();
                }
            }
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
        private void process() {
            String requestContent = request.toString().toUpperCase(); // 请求内容转大写作为输出
            byte[] response = requestContent.getBytes(StandardCharsets.UTF_8);
            output.put(response);
        }
        // 缓存每次读取的内容
        StringBuilder request = new StringBuilder();
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
                        request.append((char)ch);
                    }
                }
            } else if (bytes == -1) {
                // -1 客户端关闭了连接
                throw new EOFException();
            } else {} // bytes == 0 继续读取
            return false;
        }
    }

}
