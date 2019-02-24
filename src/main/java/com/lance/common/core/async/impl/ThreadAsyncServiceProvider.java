package com.lance.common.core.async.impl;


import com.lance.common.core.async.AsyncServiceProvider;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 线程池
 *
 * @author lance
 */
public class ThreadAsyncServiceProvider implements AsyncServiceProvider
{
    private ExecutorService executorService = null;
    private int corePoolSize = 50;

    public ThreadAsyncServiceProvider()
    {
        this.init();
    }

    public ThreadAsyncServiceProvider(int corePoolSize)
    {
        this.corePoolSize = corePoolSize;
        this.init();
    }

    private void init()
    {
        this.executorService = new ScheduledThreadPoolExecutor(corePoolSize, new BasicThreadFactory.Builder().namingPattern("spAsync-%d").daemon(true).build());
    }

    @Override
    public void process(Runnable runnable)
    {
        this.executorService.execute(runnable);
    }
}
