package com.su.shorturl.modules.shorter.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 短网址生成
 */
@Data
public class GenShortUrlReqVO {
    @NotBlank(message = "地址不能为空")
    @Length(max = 300, message = "地址最大长度不能超过300个字符")
    @Pattern(regexp = "^(http|ftp|https):\\/\\/([^/:]+)(:\\d*)?(.*)$", message = "地址最大长度不能超过300个字符")
    private String url; //地址

}
