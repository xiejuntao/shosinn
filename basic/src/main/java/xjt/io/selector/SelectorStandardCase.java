package xjt.io.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@Slf4j
public class SelectorStandardCase {
    public static void main(String[] args) throws IOException {
        int port = 2049;
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        log.info("server started`port={}",port);
        serverSocketChannel.configureBlocking(false);
        Selector selector= Selector.open();
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            log.info("waiting for io event.");
            int count = selector.select();
            if(count==0){
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey tmpSelectionKey = iterator.next();
                if (tmpSelectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.write(ByteBuffer.wrap("hi".getBytes()));

                    log.info("server say hi`interestOps={}`channel={}", tmpSelectionKey.interestOps(), tmpSelectionKey.channel());
                }
                iterator.remove();
            }
        }
    }
}
/**
 * 待完成
 * 1、socket注册selector，完成输入和输出响应。
 * 2、模拟处理过长，selectedKeys集合更改的情况。
 * 3、再完善Reactor代码
 * https://time.geekbang.org/column/article/8805
 * 4、复习netty。
 * */
