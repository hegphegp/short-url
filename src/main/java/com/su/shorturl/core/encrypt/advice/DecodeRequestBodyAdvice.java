package com.su.shorturl.core.encrypt.advice;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.su.shorturl.core.Exception.ArgNotValidRuntimeException;
import com.su.shorturl.core.Exception.DecryptRuntimeException;
import com.su.shorturl.core.common.utils.MD5Utils;
import com.su.shorturl.core.common.utils.RSAUtils;
import com.su.shorturl.core.encrypt.annotation.RequestSignDecode;
import com.su.shorturl.core.encrypt.properties.ParamEncryptProperties;
import com.su.shorturl.core.encrypt.request.SignEcryptParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.util.Objects;

@Slf4j
@Component
@ControllerAdvice(basePackages = "com.su.shorturl.modules.shorter.controller")
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private RSAPrivateKey rsaPrivateKey; //RSA 加密私钥

    @Autowired
    private ParamEncryptProperties paramEncryptProperties;

    @PostConstruct
    public void init() {
        try {
            BigInteger modulus = new BigInteger(this.paramEncryptProperties.getRsa().getModulusStr()); //模
            //BigInteger publicExponent = new BigInteger(this.paramEncryptProperties.getRsa().getPublicExpStr()); //公钥指数
            BigInteger privateExponent = new BigInteger(this.paramEncryptProperties.getRsa().getPrivateExpStr()); //私钥指数
            this.rsaPrivateKey = RSAUtils.getRSAPrivateKeyByModulusAndPrivateExponent(modulus, privateExponent);
        } catch (Exception e) {
            logger.error("初始化RSA加解密错误：", e);
        }
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getParameterAnnotation(RequestBody.class) != null
                && methodParameter.getMethodAnnotation(RequestSignDecode.class) != null;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage request, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage request, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        long currTimestamp = System.currentTimeMillis();
        try (InputStream body = request.getBody();) {
            String bodyStr = IoUtil.read(request.getBody(), "UTF-8");

            SignEcryptParam signEcryptParam = JSONUtil.toBean(bodyStr, SignEcryptParam.class);
            if (signEcryptParam == null) {
                throw new ArgNotValidRuntimeException("请求参数不能为空");
            }
            if (StrUtil.isBlank(signEcryptParam.getData())) {
                throw new ArgNotValidRuntimeException("数据不能为空");
            }
            //时间范围 在10秒内
            if (!(currTimestamp + 1000L > signEcryptParam.getTimestamp() && currTimestamp - 10000L < signEcryptParam.getTimestamp())) {
                throw new ArgNotValidRuntimeException("非法请求");
            }
            String salt = this.paramEncryptProperties.getSign().getSalt(); //加密盐
            String sign = MD5Utils.md5Digest(signEcryptParam.getData() + signEcryptParam.getTimestamp() + salt);
            if (!Objects.equals(sign, signEcryptParam.getSign())) {
                throw new ArgNotValidRuntimeException("签名错误");
            }

            //RSA 解密
            byte[] decode = Base64.decodeBase64(signEcryptParam.getData().getBytes("UTF-8"));
            //解密
            byte[] decrypt = RSAUtils.decrypt(this.rsaPrivateKey, decode);

            //转成新的入参
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decrypt);
            return new DecodedHttpInputMessage(request.getHeaders(), byteArrayInputStream);
        } catch (Exception e) {
            logger.error("解密入参错误：", e);
            throw new DecryptRuntimeException("入参错误！");
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    static class DecodedHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;

        public DecodedHttpInputMessage(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
