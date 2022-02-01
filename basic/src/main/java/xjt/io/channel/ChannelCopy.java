package xjt.io.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopy {
    public static void main(String[] args) throws IOException {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);
        //channelCopyV1(source,dest);
        channelCopyV2(source,dest);
        source.close();
        dest.close();
    }

    private static void channelCopyV1(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16*1024);
        while(source.read(buffer)!=-1){
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()){
            dest.write(buffer);
        }
    }
    private static void channelCopyV2(ReadableByteChannel source,WritableByteChannel dest) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(16*1024);
        while (source.read(byteBuffer)!=-1){
            byteBuffer.flip();
            while(byteBuffer.hasRemaining()){
                dest.write(byteBuffer);
            }
            byteBuffer.clear();
        }
    }
}
