package xjt.io.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ChannelAccept {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap("人生如斯".getBytes(StandardCharsets.UTF_8));
        ServerSocketChannel serverSocketChannel = null;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(2046));

        //serverSocketChannel.configureBlocking(false);
        while (true){
            log.info("waiting for connections");
            SocketChannel socketChannel = serverSocketChannel.accept();//非阻塞模式,立即返回
            if(socketChannel==null){
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
            }else{
                log.info("incoming connection`address={}",socketChannel.getRemoteAddress());

                //rewind(buffer);
                //buffer.position(0);

                socketChannel.write(buffer);
                flip(buffer);
                log.info("write buffer");
                //socketChannel.close();
            }
        }
    }
    /**
     * 倒带？
     */
    public static void rewind(ByteBuffer buffer){
        log.info("before rewind`limit={}`position={}`mark={}`capacity={}"
                ,buffer.limit(),buffer.position(),buffer.mark(),buffer.capacity());
        buffer.rewind();
        log.info("after rewind`limit={}`position={}`mark={}`capacity={}"
                ,buffer.limit(),buffer.position(),buffer.mark(),buffer.capacity());
    }
    /**
     * 翻转
     */
    public static void flip(ByteBuffer buffer){
        log.info("before flip`limit={}`position={}`mark={}`capacity={}"
                ,buffer.limit(),buffer.position(),buffer.mark(),buffer.capacity());
        buffer.flip();
        log.info("after flip`limit={}`position={}`mark={}`capacity={}"
                ,buffer.limit(),buffer.position(),buffer.mark(),buffer.capacity());
    }
}
