package xjt.io.pipe;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
@Slf4j
public class PipeTest {
    public static void main(String[] args) throws IOException {
        WritableByteChannel outChannel = Channels.newChannel(System.out);
        Pipe pipe = Pipe.open();
        Worker worker = new Worker(pipe.sink());
        //worker.start();
        ReadableByteChannel workChannel = pipe.source();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        log.info("waiting worker");
        while (workChannel.read(buffer)>0){//blocking when read byte is null.
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
    }
}
