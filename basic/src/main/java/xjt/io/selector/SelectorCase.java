package xjt.io.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class SelectorCase {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(2047));
        serverSocketChannel.configureBlocking(false);
        Selector selector0 = Selector.open();
        //Selector selector1 = Selector.open();
        //log.info("multi selector`assert={}",selector0!=selector1);
        SelectionKey selectionKey = serverSocketChannel.register(selector0, SelectionKey.OP_ACCEPT);//.interestOps(SelectionKey.OP_WRITE);
        log.info("selection key multi interest`ops={}",selectionKey.interestOps());
        Pipe pipe = Pipe.open();
        //selectionKey.attach(new Worker(pipe.sink()));
        while (true) {
            int count = selector0.select();
            log.info("selection keys`count={}",count);
            if(count>0){
                Set<SelectionKey> selectionKeys = selector0.keys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()) {
                    SelectionKey tmpSelectionKey = iterator.next();
                    if (tmpSelectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //socketChannel.write(ByteBuffer.wrap("hi".getBytes()));
                        //socketChannel.
                        log.info("server say hi");
                    }
                }
                //selectionKeys.clear();
            }
        }
    }
}
