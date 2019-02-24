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
public class LogBackInfoFilter extends Filter<ILoggingEvent>
{
    @Override
    public FilterReply decide(ILoggingEvent event)
    {
        Level level = event.getLevel();
        String loggerName = event.getLoggerName();

        // 允许输出sql日志
        if (level == Level.DEBUG)
        {
            if (loggerName.contains("druid.sql.Statement") || loggerName.contains("druid.sql.ResultSet"))
            {
                return FilterReply.ACCEPT;
            }
            else
            {
                return FilterReply.DENY;
            }
        }

        if (level == Level.INFO || level == Level.WARN || level == Level.ERROR)
        {
            if ("com.alibaba.druid.sql.SQLUtils".equals(loggerName) || "com.alibaba.druid.filter.stat.StatFilter".equals(loggerName))
            {
                return FilterReply.DENY;
            }
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
