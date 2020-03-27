package com.su.shorturl.modules.shorter.controller;

import com.su.shorturl.core.common.response.ResultBean;
import com.su.shorturl.core.encrypt.annotation.RequestSignDecode;
import com.su.shorturl.modules.shorter.service.ShortUrlService;
import com.su.shorturl.modules.shorter.vo.GenShortUrlReqVO;
import com.su.shorturl.modules.shorter.vo.ShortUrlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 短链
 */
@Controller
@RequestMapping("/")
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlGetService;

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    /**
     * 获取短网址重定向地址
     *
     * @param keyid
     * @return
     */
    @GetMapping("/{keyid}")
    public String toRealUrl(@PathVariable("keyid") String keyid) {
        String longUrl = this.shortUrlGetService.toRealUrl(keyid);
        return "redirect:" + longUrl;
    }

    /**
     * 短网址生成
     *
     * @param reqVO
     * @return
     */
    @ResponseBody
    @PostMapping("/shortUrl/genShortUrl")
    public ResultBean<ShortUrlVO> genShortUrl(@Valid @RequestBody GenShortUrlReqVO reqVO) {
        ShortUrlVO shortUrlVO = this.shortUrlGetService.genShortUrl(reqVO.getUrl());
        return ResultBean.success(shortUrlVO);
    }


    /**
     * api方式 短网址生成（加密）
     * 调用方式参考com.su.shorturl.modules.shorter.controller.ShortUrlControllerTest#genShortUrlByApi_sign()
     *
     * @param reqVO
     * @return
     */
    @RequestSignDecode
    @ResponseBody
    @PostMapping("/api/genShortUrl")
    public ResultBean<ShortUrlVO> genShortUrlByApi(@Valid @RequestBody GenShortUrlReqVO reqVO) {
        ShortUrlVO shortUrlVO = this.shortUrlGetService.genShortUrl(reqVO.getUrl());
        return ResultBean.success(shortUrlVO);
    }

}
