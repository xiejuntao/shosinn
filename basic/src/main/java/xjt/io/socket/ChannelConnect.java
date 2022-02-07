package xjt.io.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ChannelConnect {
    public static void main(String[] args) throws IOException {
        SocketChannel client = SocketChannel.open();
        client.connect(new InetSocketAddress("localhost",2046));
        ByteBuffer buffer = ByteBuffer.allocate(2046);
        int count = client.read(buffer);
        if(count>0){
            String s = new String(buffer.array(),0,count, StandardCharsets.UTF_8);
            System.out.println(s);
        }
    }
}
