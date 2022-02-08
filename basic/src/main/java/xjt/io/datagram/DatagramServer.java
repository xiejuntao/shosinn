package xjt.io.datagram;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
@Slf4j
public class DatagramServer {
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(2047));
        //datagramChannel.configureBlocking(false);//阻塞模式下，datagramChannel.receive将阻塞处理，否则立即返回null。
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            log.info("waiting connection");
            buffer.clear();
            SocketAddress remoteAddress = datagramChannel.receive(buffer);
            if(remoteAddress!=null){
                String receiveStr = new String(buffer.array(),0,buffer.position(), StandardCharsets.UTF_8);
                log.info("receive`address={}`receiveStr={}",remoteAddress,receiveStr);
            }
        }
    }
}
