package com.su.shorturl.core.common.response;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 返回状态代码标识
 */
@Getter
public enum ResultCode {
    //操作成功
    SUCCESS(true, 1, "操作成功！"),

    //不存在
    NOT_FOUND(false,4010, "接口不存在"),

    //异常
    PARAM_ERROR(false,5010, "入参异常"),
    PARAM_FORMAT_ERROR(false,5090, "入参格式异常"),
    DECRYPT_ERROR(false,5610, "入参解密异常"),
    DUPLICATE_KEY(false, 5710, "数据库中已存在该记录"),
    UNKNOWN_EXCEPTION(false, 5999, "未知异常");//未知异常


//    PATH_ERROR(false, 404, "请求的URL不存在，请检查请求URL是否正确！"),
//    FAIL(false, 11111, "操作失败！"),
//    UNAUTHENTICATED(false, 10001, "此操作需要登陆系统！"),
//    UNAUTHORISE(false, 10002, "权限不足，无权操作！"),
//    VCODE_GET_ERROR(false, 10003, "图片验证码获取失败！"),
//    INVALID_PARAM(false, 10003, "非法参数"),
//    VCODE_NULL(false, 10004, "验证码不能为空！"),
//    VCODE_ERROR(false, 10005, "验证码输入有误，请重新输入！"),
//    PHONE_MAIL_ERROR(false, 10006, "手机号和邮箱校验失败！"),
//    UPDATE_ERROR(false, 10008, "更新失败！"),
//    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！"),
//
//    AUTH_USERNAME_NONE(false, 23001, "请输入账号！"),
//    AUTH_PASSWORD_NONE(false, 23002, "请输入密码！"),
//    AUTH_VERIFYCODE_NONE(false, 23003, "请输入验证码！"),
//    AUTH_ACCOUNT_NOTEXISTS(false, 23004, "账号不存在！"),
//    AUTH_CREDENTIAL_ERROR(false, 23005, "账号或密码错误！"),
//    AUTH_LOGIN_ERROR(false, 23006, "登陆过程出现异常请尝试重新操作！"),
//    AUTH_LOGIN_APPLYTOKEN_FAIL(false, 23007, "账号或密码错误！"),
//    //AUTH_LOGIN_APPLYTOKEN_FAIL(false,23007,"申请令牌失败！"),
//    AUTH_LOGIN_TOKEN_SAVEFAIL(false, 23008, "存储令牌失败！"),
//    AUTH_LOGOUT_FAIL(false, 23009, "退出失败！");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    //异常时间
    String date;


    private ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.date = LocalDateTime.now().toString();
    }
}
