package com.su.shorturl.core.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回对象包装类（带泛型）
 */
@Data
public class ResultBean<T> implements Serializable {
    /**
     * 时间戳
     */
    private long timestamp;
    /**
     * 操作是否成功
     */
    private boolean success;
    /**
     * 操作代码
     */
    private int code;
    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    public ResultBean() {
        this(ResultCode.SUCCESS);
    }

    public ResultBean(ResultCode resultCode) {
        this.success = resultCode.success;
        this.code = resultCode.code;
        this.message = resultCode.message;
        this.timestamp = System.currentTimeMillis();
    }

    public ResultBean(ResultCode resultCode, String message) {
        this.success = resultCode.success;
        this.code = resultCode.code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }


    public static ResultBean success() {
        return new ResultBean();
    }

    public static <E> ResultBean<E> success(E data) {
        ResultBean<E> resultBean = new ResultBean<E>();
        resultBean.setData(data);
        return resultBean;
    }

    public static ResultBean err(ResultCode resultCode) {
        return new ResultBean(resultCode);
    }

    public static ResultBean err(ResultCode resultCode, String message) {
        return new ResultBean(resultCode, message);
    }
}
