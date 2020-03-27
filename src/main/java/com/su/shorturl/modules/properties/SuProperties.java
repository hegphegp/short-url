package com.su.shorturl.modules.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "su")
public class SuProperties {
    private Long shortUrlCacheTime = 2L; //缓存时间，单位分钟

    private String domain; //域名
    private String shortUrlDomain; //短链域名

    /**
     * 获取域名
     *
     * @return
     */
    public String obtainDomain() {
        return this.domain.endsWith("/") ? this.domain : this.domain + "/";
    }
}
