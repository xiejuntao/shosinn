package xjt.io.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
@Slf4j
public class ChannelConnect {
    public static void main(String[] args) throws IOException {
        SocketChannel client = SocketChannel.open();
        client.configureBlocking(false);
        log.info("client try connect");
        boolean isConnected = client.connect(new InetSocketAddress("localhost",2046));
        log.info("client try connect`isConnected={}",isConnected);//服务不通。非阻塞模式返回连接状态为false。阻塞模式抛出连接异常。
/*        ByteBuffer buffer = ByteBuffer.allocate(2046);
        int count = client.read(buffer);
        if(count>0){
            String s = new String(buffer.array(),0,count, StandardCharsets.UTF_8);
            System.out.println(s);
        }*/
    }
}
