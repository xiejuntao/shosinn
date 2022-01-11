package xjt.sb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class RedisService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    public void get(){
        String k = "k";
        String k2 = "k2";
        stringRedisTemplate.opsForValue().set(k,"kk");
        redisTemplate.opsForValue().set(k2,"kkkk");

        log.info("string`key={}`value={}",k,stringRedisTemplate.opsForValue().get(k));
        log.info("obj`key={}`value={}",k,redisTemplate.opsForValue().get(k));

        log.info("string`key={}`value={}",k2,stringRedisTemplate.opsForValue().get(k2));
        log.info("obj`key={}`value={}",k2,redisTemplate.opsForValue().get(k2));
    }
}
