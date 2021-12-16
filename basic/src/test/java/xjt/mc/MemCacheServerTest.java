package xjt.mc;

import org.junit.jupiter.api.Test;
import xjt.scalableio.mc.MemCacheServer;

public class MemCacheServerTest {
    @Test
    public void testServer(){
        MemCacheServer memCacheServer = new MemCacheServer(11777);
        memCacheServer.start();
    }
}
