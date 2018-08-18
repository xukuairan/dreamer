package com.krxu.dreamer.redis.manager;

/**
 * @author xukuairan
 * @version [版本号]
 * @date 2018-07-30
 * @description [添加描述]
 */
public interface RedisCacheManager {

    /**
     * 取值
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 存入缓存不失效
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    <T> String set(String key, T value);

    /**
     * 存入缓存，失效时间：单位：秒
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    <T> String set(String key, T value, int expireTime);

    /**
     * 缓存是否存在
     *
     * @param key
     * @return
     */
    boolean exists(String key);
}
