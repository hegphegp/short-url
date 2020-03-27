package com.su.shorturl.modules.shorter.repository;

import com.su.shorturl.modules.shorter.dao.mapper.ShortUrlMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;

/**
 * redis ID 生成器
 */
@Component
public class RedisLongIdGenerator {
    private final static int RANDOM_STEP_SIZE = 10; //随机步长最大值为10
    private final static String SHORT_URL_INCREMENT_HASH_KEY = "shortUrl:incrementHashKey"; //自增hash key
    private final static String ID_KEY = "idkey"; //自增主键

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ShortUrlMappingMapper shortUrlMappingMapper;


    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        if (!stringRedisTemplate.hasKey(SHORT_URL_INCREMENT_HASH_KEY)) {
            Long maxKeyidLong = this.shortUrlMappingMapper.findMaxkeyidLong();
            if (maxKeyidLong == null) {
                maxKeyidLong = 3844L; //62进制3位长度 62^(2)
            }
            this.stringRedisTemplate.opsForHash().putIfAbsent(SHORT_URL_INCREMENT_HASH_KEY, ID_KEY, Long.toString(maxKeyidLong));
        }
    }

    /**
     * 下一个随机自增序列
     *
     * @return
     */
    public Long next() {
        return this.stringRedisTemplate.opsForHash().increment(SHORT_URL_INCREMENT_HASH_KEY, ID_KEY, this.getRandomStep());
    }

    /**
     * 获取随机步长
     *
     * @return
     */
    private long getRandomStep() {  //+1 避免出现0
        return (long) (new Random().nextInt(RANDOM_STEP_SIZE) + 1);
    }

}
