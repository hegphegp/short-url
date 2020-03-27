package com.su.shorturl.modules.shorter.repository;

import org.springframework.stereotype.Component;

/**
 * redis key配置
 */
@Component
public class RedisKeyConfig {
    //短链key
    private final static String SHORT_URL_KEY = "shortUrl:%s";


    //////////////////////////////////////////////////////////////////

    /**
     * 短链key
     *
     * @param keyid 关键id
     * @return
     */
    public String getShortUrlKey(String keyid) {
        return String.format(SHORT_URL_KEY, keyid);
    }


}
