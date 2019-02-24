package com.lance.common.config.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * druid不能够格式化package,会产生无法格式化日志，故阻挡其日志输出
 *
 * @author lance
 */
public class LogBackWarnFilter extends Filter<ILoggingEvent>
{
    @Override
    public FilterReply decide(ILoggingEvent event)
    {
        if (event.getLevel() == Level.WARN)
        {
            String loggerName = event.getLoggerName();
            if ("com.alibaba.druid.sql.SQLUtils".equals(loggerName) || "com.alibaba.druid.filter.stat.StatFilter".equals(loggerName))
            {
                return FilterReply.DENY;
            }
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
