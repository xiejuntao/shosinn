package xjt.mds.kafka;

import lombok.extern.slf4j.Slf4j;
import xjt.mds.MdsStreamHandler;
import xjt.mds.kafka.request.KafkaRequestType;

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
    private String tip = "k>";
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
                log.info("ready accept request");
                int size = selector.select(); // 阻塞，直到有通道事件就绪
                log.info("accept request`size={}",size);
                if(size>0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if(selectionKey.isAcceptable()){
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            if(socketChannel!=null) {
                                socketChannel.configureBlocking(false);
                                socketChannel.write(ByteBuffer.wrap(tip.getBytes()));
                                socketChannel.register(selector, SelectionKey.OP_READ);
                                log.info("accept connection");
                            }
                        }
                        if(selectionKey.isReadable()){
                            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                            MdsStreamHandler mdsStreamHandler = new MdsStreamHandler(socketChannel,tip);
                            log.info("begin read req");
                            mdsStreamHandler.read(new MdsStreamHandler.MdsReadStreamCallback() {
                                @Override
                                public void readCompleted(String req) {
                                    try {
                                        log.info("finish read req`data={}",req);
                                        requestQueue.put(new KafkaRequest(selectionKey, KafkaRequestType.PRODUCE,req));
                                    } catch (InterruptedException e) {
                                        log.error("read req error",e);
                                    }
                                }
                            });
                        }
                        if(selectionKey.isWritable()){
                            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                            String res = (String)selectionKey.attachment();
                            log.info("response`result={}",res);
                            MdsStreamHandler mdsStreamHandler = new MdsStreamHandler(socketChannel,tip);
                            mdsStreamHandler.writeRes(res, new MdsStreamHandler.MdsWriteStreamCallback() {
                                @Override
                                public void closeSession() throws IOException {
                                    socketChannel.close();
                                }
                                @Override
                                public void writeCompleted() {
                                    selectionKey.interestOps(SelectionKey.OP_READ);
                                }
                            });
                        }
                    }
                    selectionKeys.clear();
                }
            }catch (Exception e){
                log.info("accept request error",e);
            }
        }
    }

    @Override
    public void run() {
        acceptRequest();
    }
}
