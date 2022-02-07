package xjt.io.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
@Slf4j
public class ChannelCopy {
    public static void main(String[] args) throws IOException {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);
        channelCopyV1(source,dest);
        //channelCopyV2(source,dest);
        source.close();
        dest.close();
    }

    private static void channelCopyV1(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16*1024);
        while(source.read(buffer)!=-1){
            log.info("flip");
            buffer.flip();//limit=position,position=0,
            dest.write(buffer);
            //buffer.compact();//position=limit - position,limit=capacity,mark=-1
            buffer.clear();//position=0;limit=capacity
        }
/*        log.info("last flip");
        buffer.flip();
        while (buffer.hasRemaining()){
            dest.write(buffer);
        }*/
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
