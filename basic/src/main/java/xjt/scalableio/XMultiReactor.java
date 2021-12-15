package xjt.scalableio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
@Slf4j
public class XMultiReactor {
    private static final int POOL_SIZE = 3;

    // Reactor（Selector） 线程池，其中一个线程被 mainReactor 使用，剩余线程都被 subReactor 使用
    static Executor selectorPool = Executors.newFixedThreadPool(POOL_SIZE);

    // 主 Reactor，接收连接，把 SocketChannel 注册到从 Reactor 上
    private Reactor mainReactor;

    // 从 Reactors，用于处理 I/O，可使用 BasicHandler 和  MultithreadHandler 两种处理方式
    private Reactor[] subReactors = new Reactor[POOL_SIZE - 1];
    int next = 0;
    private int port;
    public XMultiReactor(int port) {
        try {
            this.port = port;
            mainReactor = new Reactor();
            for (int i = 0; i < subReactors.length; i++) {
                subReactors[i] = new Reactor();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 启动主从 Reactor，初始化并注册 Acceptor 到主 Reactor
     */
    public void start() throws IOException {
        Thread mainThread = new Thread(mainReactor);
        mainThread.setName("mainReactor");
        new Acceptor(mainReactor.getSelector(), port); // 将 ServerSocketChannel 注册到 mainReactor

        selectorPool.execute(mainThread);

        for (int i = 0; i < subReactors.length; i++) {
            Thread subThread = new Thread(subReactors[i]);
            subThread.setName("subReactor-" + i);
            selectorPool.execute(subThread);
        }
    }
    static class Reactor implements Runnable {
        private ConcurrentLinkedQueue<XHandler> events = new ConcurrentLinkedQueue<>();
        final Selector selector;

        public Reactor() throws IOException {
            selector = Selector.open();
        }
        public Selector getSelector() {
            return selector;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) { // 死循环
                    XHandler handler = null;
                    while ((handler = events.poll()) != null) {
                        handler.socketChannel.configureBlocking(false); // 设置非阻塞
                        // Optionally try first read now
                        handler.selectionKey = handler.socketChannel.register(selector, SelectionKey.OP_READ); // 注册通道
                        handler.selectionKey.attach(handler); // 管理事件的处理程序
                    }

                    selector.select(); // 阻塞，直到有通道事件就绪
                    Set<SelectionKey> selected = selector.selectedKeys(); // 拿到就绪通道 SelectionKey 的集合
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()) {
                        SelectionKey skTmp = it.next();
                        dispatch(skTmp); // 根据 key 的事件类型进行分发
                    }
                    selected.clear(); // 清空就绪通道的 key
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void dispatch(SelectionKey selectionKey) {
            Runnable r = (Runnable) (selectionKey.attachment()); // 拿到通道注册时附加的对象
            if (r != null){
                r.run();//TODO 线程方式启动？
            }
        }

        public void reigster(XHandler xHandler) {
            events.offer(xHandler);
            selector.wakeup();
        }
    }
    class Acceptor implements Runnable{
        final Selector selector;
        final ServerSocketChannel serverSocket;
        public Acceptor(Selector selector, int port) throws IOException{
            this.selector = selector;
            serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(port)); // 绑定端口
            // 设置成非阻塞模式
            serverSocket.configureBlocking(false);
            // 注册到 选择器 并设置处理 socket 连接事件
            SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            sk.attach(this);
            log.info("mainReactor-" + "Acceptor: Listening on port: " + port);
        }
        @Override
        public void run() {
            try {
                // 接收连接，非阻塞模式下，没有连接直接返回 null
                SocketChannel socketChannel = serverSocket.accept();
                if (socketChannel != null) {
                    // 把提示发到界面
                    socketChannel.write(ByteBuffer.wrap("Implementation of Reactor Design Partten by xjt\r\nreactor> ".getBytes()));

                    System.out.println("mainReactor-" + "Acceptor: " + socketChannel.socket().getLocalSocketAddress() +" 注册到 subReactor-" + next);
                    // 将接收的连接注册到从 Reactor 上

                    // 发现无法直接注册，一直获取不到锁，这是由于 从 Reactor 目前正阻塞在 select() 方法上，此方法已经
                    // 锁定了 publicKeys（已注册的key)，直接注册会造成死锁

                    // 如何解决呢，直接调用 wakeup，有可能还没有注册成功又阻塞了。这是一个多线程同步的问题，可以借助队列进行处理
                    Reactor subReactor = subReactors[next];
                    subReactor.reigster(new XHandler(socketChannel));
                    //new MultithreadHandler(selector, sc);//TODO 工作线程多线程
                    if(++next == subReactors.length) {
                        next = 0;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        XMultiReactor mr = new XMultiReactor(10397);
        mr.start();
    }
}
