package com.krxu.dreamer.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author Administrator
 * @version [版本号]
 * @date 2018/8/13
 * @description [添加描述]
 */
public class JedisTest {

    private Jedis jedis;

    @Before
    public void setJedis() {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    @Test
    public void test() {
        String key = "xukuairan:test:string";
        jedis.set(key,"heyyyyy0");
        jedis.expire(key, 1000);
        System.out.println(jedis.get(key));
    }
}
