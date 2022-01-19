package xjt.mds.kafka;

import lombok.extern.slf4j.Slf4j;
import xjt.mds.MdsStreamHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
@Slf4j
public class KafkaNetAcceptor implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private LinkedBlockingQueue<KafkaRequest> requestQueue;
    public KafkaNetAcceptor(ServerSocketChannel serverSocketChannel, LinkedBlockingQueue requestQueue) {
        try {
            this.serverSocketChannel = serverSocketChannel;
            this.requestQueue = requestQueue;
            this.selector = Selector.open();
            this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void acceptRequest(){
        while (true){
            try {
                int size = selector.select(); // 阻塞，直到有通道事件就绪
                if(size>0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if(selectionKey.isAcceptable()){
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ).attach(new MdsStreamHandler(socketChannel,"k>"));
                        }
                        if(selectionKey.isReadable()){
                            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                            MdsStreamHandler mdsStreamHandler = (MdsStreamHandler) selectionKey.attachment();
                            mdsStreamHandler.read(new MdsStreamHandler.MdsReadStreamCallback() {
                                @Override
                                public void readCompleted(String req) {
                                    try {
                                        requestQueue.put(new KafkaRequest(selectionKey,req));
                                    } catch (InterruptedException e) {
                                        log.error("accept error",e);
                                    }
                                }
                            });
                        }
                        if(selectionKey.isWritable()){
                            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                            String res = (String)selectionKey.attachment();
                            socketChannel.write(ByteBuffer.wrap(res.getBytes()));
                            socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        }
                    }
                    selectionKeys.clear();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        acceptRequest();
    }
}
