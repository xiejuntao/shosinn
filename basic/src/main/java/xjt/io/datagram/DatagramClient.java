package xjt.io.datagram;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class DatagramClient {
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost",2047);
        datagramChannel.connect(inetSocketAddress);
        ByteBuffer byteBuffer = ByteBuffer.wrap("有趣".getBytes(StandardCharsets.UTF_8));
        datagramChannel.write(byteBuffer);
    }
}
