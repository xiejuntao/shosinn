package xjt.mds.redis;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class TestJedis {
    public static void main(String[] args) throws IOException {
        Jedis jedis = new Jedis("localhost",6379);
        String v = jedis.get("a");
        log.info("v={}",v);
    }
}
