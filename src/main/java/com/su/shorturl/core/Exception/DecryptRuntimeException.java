package com.su.shorturl.core.Exception;

import com.su.shorturl.core.common.response.ResultCode;
import lombok.Data;

/**
 * 解密异常
 */
@Data
public class DecryptRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = ResultCode.DECRYPT_ERROR.getCode();

    public DecryptRuntimeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public DecryptRuntimeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public DecryptRuntimeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public DecryptRuntimeException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }


}
