package com.su.shorturl;

import cn.hutool.core.codec.Base62;
import cn.hutool.core.convert.Convert;
import com.su.shorturl.core.idGenerator.RedisLongIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class ShortUrlApplication implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlApplication.class, args);
        System.out.println(">>短链服务启动成功<<");
    }

    // RedisLongIdGenerator
    @Override
    public void run(String... args) throws Exception {
        RedisLongIdGenerator redisLongIdGenerator = new RedisLongIdGenerator(stringRedisTemplate, "key", 1000, 1000L);
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            redisLongIdGenerator.next();
        }
        Long end = System.currentTimeMillis();
        System.out.println("===>>>>"+(end-start));



    }
}
