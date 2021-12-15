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
@Slf4j
public class XReactor implements Runnable{
    final Selector selector;
    final ServerSocketChannel serverSocketChannel;
    public XReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel
                .register(selector,SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());
        log.info("server stared`port={}",port);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                int size = selector.select();
                if(size>0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        dispath(iterator.next());
                    }
                    selectionKeys.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void dispath(SelectionKey selectionKey){
        Object obj = selectionKey.attachment();
        log.info("dispatch`obj={}`acceptor={}",obj,obj instanceof Acceptor);
        Runnable runnable = (Runnable) selectionKey.attachment();
        if(runnable!=null){
            runnable.run();//并没有启动线程
        }
/*        Acceptor acceptor = (Acceptor)obj;
        acceptor.run();*/
    }

    private class Acceptor implements Runnable{
        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if(socketChannel!=null){
                    // 把提示发到界面
                    socketChannel.write(ByteBuffer.wrap("Implementation of Reactor Design Partten by xjt\r\nreactor> ".getBytes()));
                    //new XHandler(selector,socketChannel);
                    new XThreadPoolHandler(selector,socketChannel);//工作处理使用线程
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        try {
            Thread th = new Thread(new XReactor(10393));
            th.setName("Reactor");
            th.start();
            th.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
