package com.su.shorturl.core.encrypt.request;

import lombok.Data;

/**
 * 签名方式入参
 */
@Data
public class SignEcryptParam {
    private long timestamp; //时间戳
    private String sign; //签名
    private String data; //json数据
}
