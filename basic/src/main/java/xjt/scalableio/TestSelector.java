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
public class TestSelector {
    public static void main(String[] args) throws IOException {
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
                        log.info("accept");
                        if(socketChannel!=null) {
                            socketChannel.configureBlocking(false);
                            handle(socketChannel);
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }

    private static void handle(SocketChannel socketChannel) throws IOException {
        Selector selector = Selector.open();
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
                        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                        int i = socketChannel.read(byteBuffer);
                        if (i != -1) {
                            String msg = new String(byteBuffer.array()).trim();
                            log.info("server received`message={}", msg);
                            String replyMsg = msg.toUpperCase();
                            socketChannel.write(ByteBuffer.wrap(replyMsg.getBytes()));
                            log.info("server reply upper`message={}", replyMsg);
                        }
                        //socketChannel.register(selector,SelectionKey.OP_WRITE);
                    }
/*                    if(selectionKey.isWritable()){
                        log.info("writalbe");
                    }*/
                }
                set.clear();
            }
        }
    }
}
