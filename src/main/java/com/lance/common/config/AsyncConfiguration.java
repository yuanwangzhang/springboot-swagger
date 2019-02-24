package com.lance.common.config;

import com.lance.common.core.async.impl.ThreadAsyncServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 线程池配置
 *
 * @author lance
 */
@Configuration
public class AsyncConfiguration
{
    @Bean
    public ThreadAsyncServiceProvider threadAsyncServiceProvider()
    {
        return new ThreadAsyncServiceProvider(500);
    }
}
