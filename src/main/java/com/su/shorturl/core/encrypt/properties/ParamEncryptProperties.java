package com.su.shorturl.core.encrypt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 参数加密 相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "su.encrypt")
public class ParamEncryptProperties {
    private SignEncrypt sign; //签名
    private RsaEncrypt rsa; //Rsa加密

    // 签名 加密参数
    @Data
    public static class SignEncrypt {
        String salt; //加密盐
    }

    // Rsa 加密参数
    @Data
    public static class RsaEncrypt {
        String modulusStr; //RAS 模
        String publicExpStr; //RAS 公钥指数
        String privateExpStr; //RAS 私钥指数
    }
}
