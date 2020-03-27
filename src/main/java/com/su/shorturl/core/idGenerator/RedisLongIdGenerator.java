package com.su.shorturl.core.idGenerator;

import cn.hutool.core.codec.Base62;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;

/**
 * redis ID 生成器
 * @module
 * @blame
 * @author heguopei
 * @since 20/3/27 17:57
 */
public class RedisLongIdGenerator {
    private Random random = new Random();
    private String key = null;
    private int stepSize = 10; //随机步长最大值为10
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 初始化一个key
     * @param stringRedisTemplate
     * @param key  全局唯一的key, 尽量避免覆盖了其他的key
     * @param stepSize
     * @param startNumber
     */
    public RedisLongIdGenerator(StringRedisTemplate stringRedisTemplate, String key, int stepSize, Long startNumber) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.stepSize = stepSize;
        this.key = key;
        stringRedisTemplate.opsForValue().set(key, Long.toString(startNumber));
    }

    /**
     * 如果在redis误删了key,重新生成一个key进去
     */
    public void renewInit(Long startNumber) {
        stringRedisTemplate.opsForValue().set(key, Long.toString(startNumber));
    }

    /**
     * 下一个随机自增序列
     *
     * @return
     */
    public Long next() {
        return stringRedisTemplate.opsForValue().increment(key, getRandomStep());
    }

    /**
     * 获取随机步长
     *
     * @return
     */
    private Long getRandomStep() {  //+1 避免出现0
        return (long) (random.nextInt(stepSize) + 1);
    }

}