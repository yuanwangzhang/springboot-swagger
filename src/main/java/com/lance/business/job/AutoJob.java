package com.lance.business.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 配置自动JOB的功用类
 *
 * @author lance
 */
@Component
@Slf4j
public class AutoJob
{
    /**
     * 执行定时任务 , 不启用则注释此注解
     */
    @Scheduled(cron = "10 10 10 * * ?")
    public void doJob()
    {

    }
}
