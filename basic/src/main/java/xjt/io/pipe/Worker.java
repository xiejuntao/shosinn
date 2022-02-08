package xjt.io.pipe;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
@Slf4j
public class Worker extends Thread{
    WritableByteChannel channel;
    private String[] verses={
        "多少事","从来急","天地转","光阴迫","一万年太久","只争朝夕"
    };
    public Worker(WritableByteChannel channel){
        this.channel = channel;
    }
    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        try {
            for(int i=0;i<verses.length;i++){
                buffer.clear();
                buffer.put(verses[i].getBytes(StandardCharsets.UTF_8));
                buffer.put("\r\n".getBytes());
                buffer.flip();
                int count = channel.write(buffer);
                log.info("worker write buffer`count={}",count);
            }
            this.channel.close();
        }catch (IOException e){
            log.error("worker error",e);
        }
    }
}
