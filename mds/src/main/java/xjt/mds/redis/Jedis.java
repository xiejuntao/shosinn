package xjt.mds.redis;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Jedis {
    private SocketChannel socketChannel;
    public Jedis(String host,int port) throws IOException {
        this.socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(host,port));
    }
    public void set(String key,String val){

    }
    public String get(String key){
        try{
            StringBuilder cmdBuilder = new StringBuilder();
            cmdBuilder.append("get").append(" ").append(key).append("\r\n");
            write(cmdBuilder.toString());
            String res = read();
            return res;
        }catch (IOException e){

        }
        return null;
    }
    private void write(String cmd) throws IOException {
        ByteBuffer output = ByteBuffer.allocate(1024);
        output.clear();
        output.put(cmd.getBytes());
        output.flip();
        while (output.hasRemaining()){
            socketChannel.write(output);
        }
    }
    private String read() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        ByteBuffer input = ByteBuffer.allocate(1024);
        int bytes = socketChannel.read(input);
        if (bytes > 0) {
            input.flip(); // 切换成读取模式
            while (input.hasRemaining()) {
                byte ch = input.get();
                stringBuilder.append((char)ch);
            }
        }
        return stringBuilder.toString();
    }
}
