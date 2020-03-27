package com.su.shorturl.modules.shorter.service;

import cn.hutool.core.util.StrUtil;
import com.su.shorturl.core.common.utils.ShortUrlUtil;
import com.su.shorturl.core.common.utils.Base62Number;
import com.su.shorturl.modules.shorter.dao.mapper.ShortUrlMappingMapper;
import com.su.shorturl.modules.shorter.entity.ShortUrlMapping;
import com.su.shorturl.modules.shorter.repository.RedisLongIdGenerator;
import com.su.shorturl.modules.shorter.repository.RedisRepository;
import com.su.shorturl.modules.shorter.vo.ShortUrlVO;
import com.su.shorturl.modules.properties.SuProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 短网址
 */
@Service
public class ShortUrlService {
    @Autowired
    private SuProperties suProperties;

    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private RedisLongIdGenerator redisLongIdGenerator;

    @Resource
    private ShortUrlMappingMapper shortUrlMappingMapper;

    /**
     * 获取短网址重定向地址
     *
     * @param keyid
     * @return
     */
    public String toRealUrl(String keyid) {
        //先从缓存中获取
        String longUrl = this.redisRepository.getShortUrl(keyid);
        if (longUrl != null) {
            return this.getLongUrlOr404(longUrl);
        }

        //如果缓存中没有，则查询一下数据库
        ShortUrlMapping shortUrlMapping = this.shortUrlMappingMapper.findByShortUrlKeyid(keyid);
        if (shortUrlMapping != null) {
            longUrl = shortUrlMapping.getOriginalUrl();
        } else {
            longUrl = "";
        }
        //放入缓存,防止缓存穿透
        this.redisRepository.setShortUrl(keyid, longUrl);

        //如果都没找到，则调转到404页面
        return this.getLongUrlOr404(longUrl);
    }

    /**
     * 获取404 页面或者 url页面
     *
     * @param url
     * @return
     */
    private String getLongUrlOr404(String url) {
        return StrUtil.isBlank(url) ? "/error/404" : url;
    }

    /**
     * 生成短链
     *
     * @param orgUrl
     * @return
     */
    public ShortUrlVO genShortUrl(String orgUrl) {
        if (!orgUrl.startsWith("http://") && !orgUrl.startsWith("https://")) {
            orgUrl = ("http://" + orgUrl);
        }

        //获取短链域名
        String shortUrlDomain = this.suProperties.getShortUrlDomain();

        //判断数据库中是否已经有了
        ShortUrlMapping shortUrlMapping = this.shortUrlMappingMapper.findByOriginalUrl(orgUrl);
        if (shortUrlMapping != null) {
            String keyid = shortUrlMapping.getShortUrlKeyid();
            String shortUrl = ShortUrlUtil.joinCompleteShortUrl(shortUrlDomain, keyid);

            //放入缓存,防止缓存穿透
            this.redisRepository.setShortUrl(keyid, orgUrl);

            return new ShortUrlVO(keyid, shortUrl, orgUrl, false);
        }

        //生成自增随机序列
        Long entityId = redisLongIdGenerator.next();
        //生成对应的keyid
        String keyid = Base62Number.convertDecimalToBase62(entityId);

        //插入数据库
        shortUrlMapping = new ShortUrlMapping();
        shortUrlMapping.setShortUrlKeyid(keyid);
        shortUrlMapping.setShortUrlKeyidLong(entityId);
        shortUrlMapping.setOriginalUrl(orgUrl);
        shortUrlMapping.setOriginalUrlHashcode(orgUrl.hashCode());
        shortUrlMapping.setCreateTime(new Date());
        this.shortUrlMappingMapper.insert(shortUrlMapping);

        //组装成完整的短链
        String shortUrl = ShortUrlUtil.joinCompleteShortUrl(shortUrlDomain, keyid);

        //放入缓存,防止缓存穿透
        this.redisRepository.setShortUrl(keyid, orgUrl);

        return new ShortUrlVO(keyid, shortUrl, orgUrl, true);
    }

}
