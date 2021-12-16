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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

@Slf4j
public class ReactSelector {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = AdvanceExecutors.newFixedThreadPoolWithQueueSizeByName("reactor",10, 1000);
        int port = 11002;
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port)); // 绑定端口
        // 设置成非阻塞模式
        serverSocket.configureBlocking(false);
        Selector selector = Selector.open();
        // 注册到 选择器 并设置处理 socket 连接事件
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        Reactor[] reactors = new Reactor[10];
        for (int i=0;i<reactors.length;i++){
            reactors[i] = new Reactor();
            executorService.execute(reactors[i]);
        }
        int n = 0;
        while (true) {
            int size = selector.select(); // 阻塞，直到有通道事件就绪
            log.info("main size={}", size);
            if (size > 0) {
                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> iterator = set.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocket.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.write(ByteBuffer.wrap("reactor> ".getBytes()));
                        if (socketChannel != null) {
                            socketChannel.configureBlocking(false);
                            reactors[n].register(new Worker(socketChannel));
                            if(++n == reactors.length){
                                n = 0;
                            }
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }
    static class Worker {
        private static final int MAXIN = 1024;
        private static final int MAXOUT = 1024;
        SocketChannel socketChannel;
        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);
        ExecutorService executorService = AdvanceExecutors.newFixedThreadPoolWithQueueSizeByName("worker-thread",10,100);
        public Worker(SocketChannel socketChannel) throws IOException {
            this.socketChannel = socketChannel;
        }
        public SocketChannel getSocketChannel() {
            return socketChannel;
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
                    socketChannel.write(ByteBuffer.wrap("\r\nreactor> ".getBytes()));
                    selectionKey.interestOps(SelectionKey.OP_READ);
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
            req.delete(0, req.length());
            return false;
        }
        private void process(SelectionKey selectionKey) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String requestContent = req.toString();
                    String responseContent = requestContent.toUpperCase(); // 请求内容转大写作为输出
                    log.info("process`requestContent={}`responseContent={}",requestContent,responseContent);
                    byte[] response = responseContent.getBytes(StandardCharsets.UTF_8);
                    output.put(response);
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                    selectionKey.selector().wakeup();
                }
            });

        }

        // 缓存每次读取的内容
        StringBuilder req = new StringBuilder();
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
                        req.append((char) ch);
                    }
                }
            } else if (bytes == -1) {
                // -1 客户端关闭了连接
                throw new EOFException();
            } else {
            } // bytes == 0 继续读取
            return false;
        }
    }
    static class Reactor implements Runnable {
        Selector selector;
        private ConcurrentLinkedQueue<Worker> workers = new ConcurrentLinkedQueue<Worker>();
        public void register(Worker worker) {
            workers.add(worker);
            selector.wakeup();//唤起run的select阻塞
        }

        public Reactor() {
            try {
                this.selector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                Worker worker;
                while ((worker = workers.poll()) != null) {
                    try {
                        worker.getSocketChannel().configureBlocking(false); // 设置非阻塞
                        // Optionally try first read now
                        SelectionKey selectionKey = worker.getSocketChannel().register(selector, SelectionKey.OP_READ); // 注册通道
                        selectionKey.attach(worker);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    log.info("reactor select");
                    int size = selector.select(); // 阻塞，直到有通道事件就绪
                    log.info("reactor`size={}",size);
                    if(size>0) {
                        Set<SelectionKey> selected = selector.selectedKeys(); // 拿到就绪通道 SelectionKey 的集合
                        Iterator<SelectionKey> it = selected.iterator();
                        while (it.hasNext()) {
                            SelectionKey selectionKey = it.next();
                            Worker w = (Worker) selectionKey.attachment();
                            if (w != null) {
                                w.handle(selectionKey);
                            }
                        }
                        selected.clear();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}