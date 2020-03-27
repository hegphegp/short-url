package com.su.shorturl.modules.shorter.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.su.shorturl.core.common.utils.MD5Utils;
import com.su.shorturl.core.common.utils.RSAUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShortUrlControllerTest {

    /**
     * 测试生成短链接 sign
     */
    @Test
    void genShortUrlByApi_sign() throws Exception {
        String modulusStr = "149246585067290098379974496821592866542960688627752537853381707458418110879617540066249968682406585487936375704618086460702827822256993644826822481187588202108026140296243995589999713881082584689022640061192408462068586934866428077923899172846268634621405780207692985593351399409419171090867960278537453199809";
        String publicExpStr = "65537";

        Map<String, String> data = new HashMap<>();
        data.put("url", "http://hutool.mydoc.io/#text_319389");
        String dataStr = RSAUtils.encrypt(JSONUtil.toJsonStr(data), modulusStr, publicExpStr); //加密

        long timestamp = System.currentTimeMillis();
        String sign = MD5Utils.md5Digest(dataStr + timestamp + "su123456");

        Map<String, Object> param = new HashMap<>();
        param.put("timestamp", timestamp);
        param.put("sign", sign);
        param.put("data", dataStr);

        String url = "http://localhost:8888/s/api/genShortUrl";
        //链式构建请求
        String result2 = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE, "application/json")//头信息，多个头信息多次调用此方法即可
                //.form(paramMap)//表单内容
                .body(JSONUtil.toJsonStr(param))
                .timeout(20000)//超时，毫秒
                .execute().body();
        System.out.println(result2);
    }
}