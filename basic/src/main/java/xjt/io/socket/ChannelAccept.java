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
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.wrap("人生如斯".getBytes(StandardCharsets.UTF_8));
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocketChannel.socket().bind(new InetSocketAddress(2046));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            log.info("waiting for connections");
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if(socketChannel==null){
                    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
                }else{
                    log.info("incoming connection`address={}",socketChannel.getRemoteAddress());
                    buffer.rewind();
                    socketChannel.write(buffer);
                    socketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
