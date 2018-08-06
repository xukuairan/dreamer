package com.krxu.dreamer.redis;

import com.krxu.dreamer.redis.manager.impl.RedisCacheManagerImpl;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/6
 * @description [添加描述]
 */
public class RedisTest {
    private RedisCacheManagerImpl redisCacheManager;

    @Before
    public void init(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(300);
        config.setMaxIdle(200);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);

        RedisConnectFactory redisConnectFactory = new RedisConnectFactory();
        redisConnectFactory.setConfig(config);
        redisConnectFactory.setConnectUrls("10.211.93.169:16379,10.211.93.169:16380,10.211.93.169:16381");
        redisConnectFactory.init();
        redisCacheManager = new RedisCacheManagerImpl();
        redisCacheManager.setRedisConnectFactory(redisConnectFactory);
    }

    @Test
    public void test(){
        redisCacheManager.set("1:2:3", "xukuairan", 500);
    }

}
