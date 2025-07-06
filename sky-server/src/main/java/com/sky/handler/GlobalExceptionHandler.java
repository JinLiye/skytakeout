package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
//@Slf4j
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AliOssUtil.class);
    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){

        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

}
