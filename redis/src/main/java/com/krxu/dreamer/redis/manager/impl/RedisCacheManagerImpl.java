package com.krxu.dreamer.redis.manager.impl;

import com.krxu.dreamer.redis.RedisConnectFactory;
import com.krxu.dreamer.redis.SerializationUtil;
import com.krxu.dreamer.redis.manager.RedisCacheManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.SafeEncoder;

/**
 * @author xukuairan
 * @version [版本号]
 * @date ${date}
 * @description [添加描述]
 */
public class RedisCacheManagerImpl implements RedisCacheManager {

    private static final Logger logger = Logger.getLogger(RedisConnectFactory.class);

    private RedisConnectFactory redisConnectFactory;

    @Override
    public <T> T get(String key, Class<T> clazz) {
        if (null == key || key.trim().length() == 0) {
            return null;
        }
        T value = null;
        byte[] ret = null;
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = this.redisConnectFactory.getShardedJedis();
            ret = shardedJedis.get(SafeEncoder.encode(key));
            if (ret != null) {
                value = SerializationUtil.deserialize(ret, clazz);
                if (logger.isDebugEnabled()) {
                    logger.debug("Found in cache, key=" + key + ", value=" + value);
                }
            }
        } catch (Throwable throwable) {
            logger.error("Error happened when calling get, key=" + key + '.', throwable);
        } finally {
            this.redisConnectFactory.returnShardedJedis(shardedJedis);
        }

        return value;
    }

    @Override
    public <T> String set(String key, T value) {
        if (null == key || key.trim().length() == 0) {
            return null;
        }
        if(value == null){
            return null;
        }
        String status = null;
        ShardedJedis shardedJedis = null;
        try {
            byte[] data = SerializationUtil.serialize(value);
            shardedJedis = this.redisConnectFactory.getShardedJedis();
            status = shardedJedis.set(SafeEncoder.encode(key), data);
        } catch (Throwable throwable) {
            logger.error("Error happened when calling set, key=" + key + ", value=" + value + '.', throwable);
        } finally {
            this.redisConnectFactory.returnShardedJedis(shardedJedis);
        }
        return status;
    }

    @Override
    public <T> String set(String key, T value, int expireTime) {
        if (null == key || key.trim().length() == 0) {
            return null;
        }
        String status = null;
        ShardedJedis shardedJedis = null;
        try {
            byte[] data = SerializationUtil.serialize(value);
            shardedJedis = this.redisConnectFactory.getShardedJedis();
            status = shardedJedis.setex(SafeEncoder.encode(key), expireTime, data);
        } catch (Throwable throwable) {
            logger.error("Error happened when calling set, key=" + key + ", value=" + value + '.', throwable);
        } finally {
            this.redisConnectFactory.returnShardedJedis(shardedJedis);
        }
        return status;
    }

    @Override
    public boolean exists(String key) {
        if (null == key || key.trim().length() == 0) {
            return false;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = this.redisConnectFactory.getShardedJedis();
            return shardedJedis.exists(key);
        } catch (Throwable throwable) {
            logger.error("Error happened when exists, key=" + key, throwable);
        } finally {
            this.redisConnectFactory.returnShardedJedis(shardedJedis);
        }
        return false;
    }

    public void setRedisConnectFactory(RedisConnectFactory redisConnectFactory) {
        this.redisConnectFactory = redisConnectFactory;
    }
}
