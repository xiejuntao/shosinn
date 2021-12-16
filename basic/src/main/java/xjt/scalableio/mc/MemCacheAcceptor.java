package xjt.scalableio.mc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 接收连接
 * */
@Slf4j
public class MemCacheAcceptor {
    public MemCacheAcceptor(){

    }
    public void start(ServerSocketChannel serverSocketChannel,Selector selector){
        try {
            while (true) {
                int size = selector.select(); // 阻塞，直到有通道事件就绪
                log.info("main size={}", size);
                if (size > 0) {
                    Set<SelectionKey> set = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = set.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.write(ByteBuffer.wrap("reactor> ".getBytes()));
                            log.info("accept");
                            if (socketChannel != null) {
                                socketChannel.configureBlocking(false);
                                //TODO reactor
                            }
                        }
                        iterator.remove();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
