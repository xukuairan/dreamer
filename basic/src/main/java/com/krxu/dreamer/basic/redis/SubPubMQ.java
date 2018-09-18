package com.krxu.dreamer.basic.redis;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/18
 * @description 发布/订阅模式
 */
@Log4j
public class SubPubMQ {

    private static final String SUB_KEY = "sub_key";
    @Test
    public void test(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = RedisUtil.getJedis();
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void subscribe(String... channels) {
                        log.info("订阅:" + channels);
                        super.subscribe(channels);
                    }

                    @Override
                    public void onMessage(String channel, String message) {
                        log.info("接收到[" + channel + "] 消息 :" + message);
                        super.onMessage(channel, message);
                    }
                }, SUB_KEY);
            }
        });
        thread.start();
    }


    private Jedis jedis = RedisUtil.getJedis();



}
