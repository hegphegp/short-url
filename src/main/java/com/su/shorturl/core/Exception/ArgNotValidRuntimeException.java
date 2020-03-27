package com.su.shorturl.core.Exception;

import com.su.shorturl.core.common.response.ResultCode;
import lombok.Data;

/**
 * 参数验证异常
 */
@Data
public class ArgNotValidRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = ResultCode.PARAM_ERROR.getCode();

    public ArgNotValidRuntimeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ArgNotValidRuntimeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ArgNotValidRuntimeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ArgNotValidRuntimeException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
