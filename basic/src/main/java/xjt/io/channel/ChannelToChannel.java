package xjt.io.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

public class ChannelToChannel {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:\\xjt\\growth_git\\growth\\README.md");
        FileChannel fileChannel = fileInputStream.getChannel();
        fileChannel.transferTo(0,fileChannel.size(), Channels.newChannel(System.out));
        fileChannel.close();
        fileInputStream.close();
    }
}
