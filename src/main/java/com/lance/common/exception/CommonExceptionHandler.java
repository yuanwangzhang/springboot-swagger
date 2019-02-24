package com.lance.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 *
 * @author lance
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler
{

    /**
     * 拦截Exception类的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String exceptionHandler(Exception e)
    {
        e.printStackTrace();
        return "系统发生异常";
    }
}
