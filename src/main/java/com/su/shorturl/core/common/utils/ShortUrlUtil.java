package com.su.shorturl.core.common.utils;

public class ShortUrlUtil {

    /**
     * 转换成/结尾的域名
     *
     * @param domain 域名
     * @return
     */
    public static String toStandardDomain(String domain) {
        return domain.endsWith("/") ? domain : domain + "/";
    }

    /**
     * 拼接成完整的短链地址
     *
     * @param domain 域名
     * @param keyid  短链keyid
     * @return
     */
    public static String joinCompleteShortUrl(String domain, String keyid) {
        return String.format("%s%s", domain, keyid);
    }
}
