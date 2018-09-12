package com.krxu.dreamer.basic.redis;

import com.krxu.dreamer.redis.RedisConnectFactory;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/12
 * @description [添加描述]
 */
@Log4j
public class RedisSentinelCluster {

    private static final String REDIS_HOST = "10.211.95.246";
    private static final int REDIS_PORT = 26379;
    private static Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT);

    @Test
    public void testSentinel(){
        Jedis sentinel1 = new Jedis("10.211.95.246", 16379);
        Jedis sentinel2 = new Jedis("10.211.95.246", 16380);
        Jedis sentinel3 = new Jedis("10.211.95.246", 16381);

        log.info(sentinel1.info());
        log.info(sentinel2.info());
        log.info(sentinel3.info());
    }

    @Test
    public void testSentinelCluster() throws InterruptedException {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(300);
        config.setMaxIdle(200);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);

        RedisConnectFactory redisConnectFactory = new RedisConnectFactory();
        redisConnectFactory.setConfig(config);
        redisConnectFactory.setConnectUrls("10.211.95.246:26379,10.211.95.246:26380,10.211.95.246:26381");
        redisConnectFactory.init();

        int i = 0 ;
        while (true){
            i ++ ;
            Thread.sleep(3000);
            try{
                ShardedJedis shardedJedis = redisConnectFactory.getShardedJedis();
                String result = shardedJedis.set("a:test", "nihaoa_" + i);
                redisConnectFactory.returnShardedJedis(shardedJedis);
                log.info(result + "_" + i);
            }catch (Exception ex){
                log.info(ex.getMessage());
            }
        }
    }
}
