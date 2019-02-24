package com.lance.business.aop;


import com.lance.common.core.async.AsyncServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 请求校验+MDC等
 *
 * @author lance
 **/
@Aspect
@Component
@Slf4j
@Order(1)
public class ControllerSign
{
    /**
     * 线程池
     */
    @Autowired
    private AsyncServiceProvider asyncServiceProvider;

    @Pointcut("execution(public * com.lance.business.controller.*.*(..))")
    public void executeController()
    {
    }


    @Before("executeController()")
    public void before(JoinPoint point)
    {
        // 接收到请求，记录请求内容
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String requestIp = request.getRemoteAddr();

        String logId = UUID.randomUUID().toString();
        // 添加日志id
        MDC.put("logId", logId);

        // 获取请求参数
        String methodName = point.getSignature().getName();

        // 异步日志
        asyncServiceProvider.process(() ->
        {
            MDC.put("logId", logId);
            log.info("Method:[{}]RequestIp:[{}]", methodName, requestIp);
            MDC.clear();
        });
    }

    @AfterReturning(pointcut = "executeController()", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, Object returnValue)
    {
        // 清除日志id
        MDC.clear();
    }


}