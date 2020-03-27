package com.su.shorturl;

import com.su.shorturl.core.common.utils.RSAUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSATest {
    /**
     * 测试 RSA密钥生成指数和模
     */
    @Test
    public void generateKeyPair() throws Exception {
        KeyPair keyPair = RSAUtils.genKeyPair();
        //生成公钥和私钥
        System.out.println(">>rsaPublicKey: " + RSAUtils.getBase64StringPublicKey(keyPair));
        System.out.println(">>rsaPrivateKey: " + RSAUtils.getBase64StringPrivateKey(keyPair));
        //生成公钥 指数和模
        RSAPublicKey rsaPublicKey = RSAUtils.getRSAPublicKeyByKeyPair(keyPair);
        System.out.println(">>rsaPublicKey Mod: " + rsaPublicKey.getModulus());
        System.out.println(">>rsaPublicKey Exp: " + rsaPublicKey.getPublicExponent());
        //生成私钥 指数和模
        RSAPrivateKey rsaPrivateKey = RSAUtils.getRSAPrivateKeyByKeyPair(keyPair);
        System.out.println(">>rsaPrivateKey Mod: " + rsaPrivateKey.getModulus());
        System.out.println(">>rsaPrivateKey Exp: " + rsaPrivateKey.getPrivateExponent());
    }

    /**
     * 测试加解密（通过公钥和私钥）
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testRsaEncAndDecByBase64str() throws Exception {
        String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGyrz7aOhWfNHHX+SE3XZ6/2bXDhk5TyAR8bqkAUDEunfDFIaR9OOa0tzb1Sjw60FoUszUNvtjcRPd1jyFq6KoeaK9OmOd9Oqpe0xipkLV/PWUchMlsVInFjp7qaahmrPpVUyZx+3Vntnvka0+KI9zS7sRsj2NEhOZF7WeKCs0jAgMBAAECgYASfWn/8y7ZAJ7ySKg/QWLpE3yJeIgzOIflXjBOphjrvo7v36Z+7EdSFzH/TZ8UShkxmAqKaSLeb0UzJK1iJnkC93I17RK6PTCBBf7RrMdoUvY9dS5zaHNvqVtGyjIik4/WRMYbH8yuPex/eXfbQx3fcX83qf7yjBTU04myy+nb+QJBAPFznG5mOew3N6peyn4OmPE/PH0SNuaslFjs3JnYAsv5KNTmnFmSJUDLyNrKwNpLRVqwLoxLiFK3FwGcpikts9cCQQDvTBL1JYDMPuVzF0uREKxX5T/GmR5Huck722YHejLZq2MLA+JbjkOVQ2o66edgBXsIvAbewf5eyPNYB8mM48eVAkAxcDWomT/k8Q1x0gTuOLjyNLuC9HbPVqjtaFpnGcbA0xycMzfyUy1TjiZrPjasu9rQjeCKE3EvVpPcXaHcLqJzAkEA4ZCEDjDbx6tB/crnoEZnLjMaesm6kUG/Pn0BH+tAI446PhfbNxJTAlAZwo7ZWtnPr9wNXczfSdmCCGjOw8+yQQJADuUZbg0HcEeMLJy5FIoCg2X0RZd/il138Rt6lK9DZa/6K3uyB0gKFqAN9pSBXlDe3stBUJwUsdjmBSzJI2/WwQ==";
        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhsq8+2joVnzRx1/khN12ev9m1w4ZOU8gEfG6pAFAxLp3wxSGkfTjmtLc29Uo8OtBaFLM1Db7Y3ET3dY8hauiqHmivTpjnfTqqXtMYqZC1fz1lHITJbFSJxY6e6mmoZqz6VVMmcft1Z7Z75GtPiiPc0u7EbI9jRITmRe1nigrNIwIDAQAB";

        String text = "上小学的时候，女生喜欢爱出头的男生，男生喜欢漂亮的女生。上中学的时候，女生喜欢有偶像派的男生，男生喜欢漂亮的女生，上大学的时候，女生喜欢成熟的男生，男生喜欢漂亮的女生，到了社会，女生喜欢有钱的男人，而男人，还是喜欢漂亮的女生。这说明男人不变，女人一直在变。这是我的心里话。";
        //加密
        RSAPrivateKey rsaPrivateKey = RSAUtils.getRSAPrivateKeyByBase64StringKey(privateKeyStr);
        byte[] encrypt = RSAUtils.encrypt(rsaPrivateKey, text.getBytes("UTF-8"));
        String encode = Base64.encodeBase64String(encrypt);
        System.out.println(">>密文：" + encode);

        //解密
        byte[] decode = Base64.decodeBase64(encode.getBytes("UTF-8"));
        RSAPublicKey rsaPublicKey = RSAUtils.getRSAPublicKeyByBase64StringKey(publicKeyStr);
        byte[] decrypt = RSAUtils.decrypt(rsaPublicKey, decode);
        System.out.println(new String(decrypt, "UTF-8"));
    }

    /**
     * 测试 RSA 加解密(通过模和指数)
     */
    @Test
    public void testRsaEncAndDecByModAndExp() throws Exception {
        String modulusStr = "158490515994183287024332399015797771414535243406251395286911658004926011086702999001085995219030691780250621326838105574397175263473205089776026382999524334776390470883184421741434824960391783945115826765258367205869349393109323010665644160698456458226105721580514118151705189978353942438539595181442594426147";
        String publicExpStr = "65537";
        String privateExpStr = "12984048405828311763334309631442059214255149333173073855919994687404790477509016305855176592321525003710355767029216913025290049736601280604963389388047149216918105102491078190239510798051839334710298774618207165864949451890642056573029891052646126473458501158798858305348400447918973419302525510952596134905";

        BigInteger modulus = new BigInteger(modulusStr); //模
        BigInteger publicExponent = new BigInteger(publicExpStr); //公钥指数
        BigInteger privateExponent = new BigInteger(privateExpStr); //私钥指数

        String text = "上小学的时候，女生喜欢爱出头的男生，男生喜欢漂亮的女生。上中学的时候，女生喜欢有偶像派的男生，男生喜欢漂亮的女生，上大学的时候，女生喜欢成熟的男生，男生喜欢漂亮的女生，到了社会，女生喜欢有钱的男人，而男人，还是喜欢漂亮的女生。这说明男人不变，女人一直在变。这是我的心里话。";
        //加密
        RSAPublicKey rsaPublicKey = RSAUtils.getRSAPublicKeyByModulusAndPublicExponent(modulus, publicExponent);
        byte[] encrypt = RSAUtils.encrypt(rsaPublicKey, text.getBytes("UTF-8"));
        String encode = Base64.encodeBase64String(encrypt);
        System.out.println(">>密文：" + encode);

        //解密
        byte[] decode = Base64.decodeBase64(encode.getBytes("UTF-8"));
        RSAPrivateKey rsaPrivateKey = RSAUtils.getRSAPrivateKeyByModulusAndPrivateExponent(modulus, privateExponent);
        byte[] decrypt = RSAUtils.decrypt(rsaPrivateKey, decode);
        System.out.println(new String(decrypt, "UTF-8"));
    }

    /**
     * 测试 RSA 加解密(通过模和指数)
     */
    @Test
    public void testRsaEncAndDecByModAndExp2() throws Exception {
        String modulusStr = "158490515994183287024332399015797771414535243406251395286911658004926011086702999001085995219030691780250621326838105574397175263473205089776026382999524334776390470883184421741434824960391783945115826765258367205869349393109323010665644160698456458226105721580514118151705189978353942438539595181442594426147";
        String publicExpStr = "65537";
        String privateExpStr = "12984048405828311763334309631442059214255149333173073855919994687404790477509016305855176592321525003710355767029216913025290049736601280604963389388047149216918105102491078190239510798051839334710298774618207165864949451890642056573029891052646126473458501158798858305348400447918973419302525510952596134905";

        String text = "上小学的时候，女生喜欢爱出头的男生，男生喜欢漂亮的女生。上中学的时候，女生喜欢有偶像派的男生，男生喜欢漂亮的女生，上大学的时候，女生喜欢成熟的男生，男生喜欢漂亮的女生，到了社会，女生喜欢有钱的男人，而男人，还是喜欢漂亮的女生。这说明男人不变，女人一直在变。这是我的心里话。";
        //加密
        String encode = RSAUtils.encrypt(text, modulusStr, publicExpStr);
        System.out.println(">>密文：" + encode);

        //解密
        String decrypt = RSAUtils.decrypt(encode, modulusStr, privateExpStr);
        System.out.println(">>解密：" + decrypt);
    }


}
