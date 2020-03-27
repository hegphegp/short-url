package com.su.shorturl.modules.shorter.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.su.shorturl.modules.shorter.entity.ShortUrlMapping;
import org.apache.ibatis.annotations.Param;

/**
 * 短链和长链映射关系
 */
public interface ShortUrlMappingMapper extends BaseMapper<ShortUrlMapping> {

    /**
     * 获取最大的id值
     *
     * @return
     */
    Long findMaxkeyidLong();

    /**
     * 通过 短链后缀 获取
     *
     * @param shortUrlKeyid
     * @return
     */
    ShortUrlMapping findByShortUrlKeyid(@Param("shortUrlKeyid") String shortUrlKeyid);

    /**
     * 通过 长地址 获取
     *
     * @param originalUrl
     * @return
     */
    ShortUrlMapping findByOriginalUrl(@Param("originalUrl") String originalUrl);


}
