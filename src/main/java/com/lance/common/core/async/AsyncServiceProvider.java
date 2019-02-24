package com.lance.common.core.async;

public interface AsyncServiceProvider
{
    /**
     * 执行业务
     *
     * @param runnable
     */
    void process(Runnable runnable);
}
