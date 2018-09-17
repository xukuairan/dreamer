package com.krxu.dreamer.basic.redis;

import com.krxu.dreamer.redis.RedisConnectFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/17
 * @description [添加描述]
 */
public class RedisUtil {

    private static final String CONNECTION_URL = "120.79.167.224:6379";

    private static RedisConnectFactory redisConnectFactory;

    private static JedisPoolConfig config;

    static {
        config = new JedisPoolConfig();
        config.setMaxTotal(300);
        config.setMaxIdle(200);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        redisConnectFactory = new RedisConnectFactory();
        redisConnectFactory.setConfig(config);
        redisConnectFactory.setConnectUrls(CONNECTION_URL);
        redisConnectFactory.init();
    }

    public static ShardedJedis getShardedJedis() {
        return redisConnectFactory.getShardedJedis();
    }

    public static void returnShardedJedis(ShardedJedis shardedJedis) {
        redisConnectFactory.returnShardedJedis(shardedJedis);
    }
}
