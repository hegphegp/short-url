package com.su.shorturl.modules.shorter.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 短链和长链映射关系
 */
@Data
@TableName("surl_short_url_mapping")
public class ShortUrlMapping implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
//    private Long id = SnowFlakeUtil.getFlowIdInstance().nextId();

    private String shortUrlKeyid; //短链keyid
    private Long shortUrlKeyidLong; //短链keyid对应Long类型
    private String originalUrl; //原始地址
    private Integer originalUrlHashcode; //原始地址Hashcode

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}