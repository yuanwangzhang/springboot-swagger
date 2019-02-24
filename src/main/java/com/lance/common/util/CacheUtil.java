package com.lance.common.util;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 缓存工具类
 *
 * @author lance
 */
public class CacheUtil
{
    /**
     * 键值对集合
     */
    private final static Map<String, Entity> map = new ConcurrentHashMap<>();
    /**
     * 定时器线程池，用于清除过期缓存
     */
    private final static ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    /**
     * 添加缓存
     *
     * @param key  键
     * @param data 值
     */
    public synchronized static void put(String key, Object data)
    {
        CacheUtil.put(key, data, 0);
    }

    /**
     * 添加缓存
     *
     * @param key    键
     * @param data   值
     * @param expire 过期时间，单位：毫秒， 0表示无限长
     */
    public synchronized static void put(String key, Object data, long expire)
    {
        //清除原键值对
        CacheUtil.remove(key);
        //设置过期时间
        if (expire > 0)
        {
            Future future = EXECUTOR.schedule(() ->
            {//过期后清除该键值对
                synchronized (CacheUtil.class)
                {
                    map.remove(key);
                }
            }, expire, TimeUnit.MILLISECONDS);
            map.put(key, new Entity(data, future));
        }
        else
        {
            //不设置过期时间
            map.put(key, new Entity(data, null));
        }
    }

    /**
     * 校验缓存中是否存在key
     *
     * @param key 键
     * @return
     */
    public synchronized static boolean keyExists(String key)
    {
        Entity entity = map.get(key);
        return entity != null;
    }

    /**
     * 读取缓存
     *
     * @param key 键
     * @return
     */
    public synchronized static Object get(String key)
    {
        Entity entity = map.get(key);
        return entity == null ? null : entity.getValue();
    }

    /**
     * 读取缓存
     *
     * @param key 键
     *            * @param clazz 值类型
     * @return
     */
    public synchronized static <T> T get(String key, Class<T> clazz)
    {
        return clazz.cast(CacheUtil.get(key));
    }

    /**
     * 清除缓存
     *
     * @param key
     * @return
     */
    public synchronized static Object remove(String key)
    {
        //清除原缓存数据
        Entity entity = map.remove(key);
        if (entity == null)
        {
            return null;
        }
        //清除原键值对定时器
        Future future = entity.getFuture();
        if (future != null)
        {
            future.cancel(true);
        }
        return entity.getValue();
    }

    /**
     * 查询当前缓存的键值对数量
     *
     * @return
     */
    public synchronized static int size()
    {
        return map.size();
    }

    /**
     * 缓存实体类
     */
    private static class Entity
    {
        /**
         * 键值对的value
         */
        private Object value;
        /**
         * 定时器Future
         */
        private Future future;

        public Entity(Object value, Future future)
        {
            this.value = value;
            this.future = future;
        }

        /**
         * 获取值
         *
         * @return
         */
        public Object getValue()
        {
            return value;
        }

        /**
         * 获取Future对象
         *
         * @return
         */
        public Future getFuture()
        {
            return future;
        }
    }
}
