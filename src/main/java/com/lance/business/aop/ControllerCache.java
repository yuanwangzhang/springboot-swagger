package com.lance.business.aop;

import com.lance.common.base.model.ResultCommunication;
import com.lance.common.core.annotation.RequestSign;
import com.lance.common.util.CacheUtil;
import com.lance.common.util.Sha1Util;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 请求信息缓存
 *
 * @author lance
 */
@Aspect
@Component
@Slf4j
@Order(2)
public class ControllerCache
{

    @Pointcut("@annotation(com.lance.common.core.annotation.RequestSign)")
    public void executeMethod()
    {
    }


    /**
     * 请求信息添加缓存
     *
     * @param point
     * @return
     */
    @Around("executeMethod()")
    public Object around(ProceedingJoinPoint point)
    {
        Object[] args = point.getArgs();
        StringBuilder requestAppender = new StringBuilder();
        for (int i = 0; i < args.length; i++)
        {
            requestAppender.append(args[i]);
        }
        String requestId = Sha1Util.encode(requestAppender.toString());

        // 获取注解
        RequestSign requestSign = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(RequestSign.class);
        // 是否允许使用缓存
        boolean enableCache = requestSign.enableCache();
        // 缓存过期时间
        int expire = requestSign.expire();

        // 若使用缓存
        if (enableCache)
        {
            // 判断是否存在已在执行的报文
            boolean isExists = CacheUtil.keyExists(requestId);
            // 若存在，直接将报文返回
            if (isExists)
            {
                log.info("从缓存中返回:{}", requestId);
                return CacheUtil.get(requestId);
            }
            // 若不存在，在缓存中更新该请求报文的初始返回报文，180秒超时
            else
            {
                Object executingObj = ResultCommunication.getInstance().getExecutingModel();
                CacheUtil.put(requestId, executingObj, 180000);
            }
        }

        Object result;
        try
        {
            result = point.proceed(args);
        }
        catch (Throwable e)
        {
            log.error("业务发生异常:" + e.getMessage());
            e.printStackTrace();
            result = ResultCommunication.getInstance().getErrorModel();
        }

        // 若使用缓存
        if (enableCache)
        {
            // 缓存中更新已执行的报文，{expire}毫秒清除，可再次访问
            CacheUtil.put(requestId, result, expire);
        }
        return result;
    }
}
