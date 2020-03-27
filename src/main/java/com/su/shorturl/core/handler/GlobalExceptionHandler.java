package com.su.shorturl.core.handler;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.su.shorturl.core.common.response.ResultBean;
import com.su.shorturl.core.common.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBean handleRRException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMesssageList = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssageList.add(fieldError.getDefaultMessage());
        }
        return ResultBean.err(ResultCode.PARAM_ERROR, StrUtil.join(",", errorMesssageList));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultBean handlerNoFoundException(Exception e) {
        logger.error("未找到资源异常", e);
        return ResultBean.err(ResultCode.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultBean handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error("重复主键异常", e);
        return ResultBean.err(ResultCode.DUPLICATE_KEY);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResultBean handleJsonParseException(JsonParseException e) {
        logger.error("入参格式异常", e);
        return ResultBean.err(ResultCode.PARAM_FORMAT_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    //@ResponseBody
    public ResultBean HolidaysHandler(Exception e) {
        logger.error("全局异常", e);
        return ResultBean.err(ResultCode.UNKNOWN_EXCEPTION);
    }


}
