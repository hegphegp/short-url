package com.su.shorturl.modules.shorter.repository;


import com.su.shorturl.modules.properties.SuProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis 业务仓库
 */
@Component("redisRepository")
public class RedisRepository {
    @Autowired
    private SuProperties suProperties;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisKeyConfig redisKeyConfig; //redis key的配置


    ///////////////////////////短链 start//////////////////////////////////////////

    /**
     * 获取短链
     *
     * @param keyid 关键id
     */
    public String getShortUrl(String keyid) {
        String longUrl = this.stringRedisTemplate.opsForValue().get(this.redisKeyConfig.getShortUrlKey(keyid));
        return longUrl;
    }

    /**
     * 删除短链
     *
     * @param keyid 关键id
     */
    public void delShortUrl(String keyid) {
        this.stringRedisTemplate.delete(this.redisKeyConfig.getShortUrlKey(keyid));
    }

    /**
     * 设置短链
     *
     * @param keyid 关键id
     */
    public void setShortUrl(String keyid, String longUrl) {
        Long shortUrlCacheTime = suProperties.getShortUrlCacheTime(); //缓存时间
        this.stringRedisTemplate.opsForValue().set(this.redisKeyConfig.getShortUrlKey(keyid), longUrl, shortUrlCacheTime, TimeUnit.MINUTES);//保存时间
    }

    ///////////////////////////短链 end//////////////////////////////////////////

}
