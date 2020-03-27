package com.su.shorturl.core.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 解密签名请求数据
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestSignDecode {

}
