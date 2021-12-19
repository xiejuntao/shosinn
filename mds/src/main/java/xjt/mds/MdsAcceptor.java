package xjt.mds;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class MdsAcceptor {
    private ServerSocketChannel serverSocketChannel;
    public MdsAcceptor(ServerSocketChannel serverSocketChannel){
        this.serverSocketChannel = serverSocketChannel;
    }
    public void accept(Selector selector,SelectionKey selectionKey){
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.write(ByteBuffer.wrap(MdsConstants.SINGLETIP.getBytes()));
            socketChannel.register(selector,SelectionKey.OP_READ).attach(new MdsHandler(socketChannel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
