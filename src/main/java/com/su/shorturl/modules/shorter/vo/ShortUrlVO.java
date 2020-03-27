package com.su.shorturl.modules.shorter.vo;

import lombok.Data;

/**
 * 短链实体
 */
@Data
public class ShortUrlVO {
    private String keyid; //短链关键id
    private String shortUrl; //短链url
    private String longUrl; //长地址url
    private boolean isNew; // 是否是新的

    public ShortUrlVO() {
    }

    public ShortUrlVO(String keyid, String shortUrl, String longUrl, boolean isNew) {
        this.keyid = keyid;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.isNew = isNew;
    }
}
