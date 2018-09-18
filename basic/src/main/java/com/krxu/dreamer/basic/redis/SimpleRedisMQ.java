package com.krxu.dreamer.basic.redis;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import redis.clients.jedis.ShardedJedis;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/17
 * @description redis消息队列模式
 */
@Log4j
public class SimpleRedisMQ {
    public static final String MESSAGE_KEY = "message:queue";

    @Test
    public  void test() throws InterruptedException {
        Thread producer = new Producer("producer");
        Thread customer = new Customer("customer");

        producer.start();
        Thread.sleep(5000);
        customer.start();
    }

    /**
     * 生产者
     */
    private static class Producer extends Thread {
        private ShardedJedis jedis;
        private String producerName;
        private volatile int count;

        public Producer(String name) {
            this.producerName = name;
            jedis = RedisUtil.getShardedJedis();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Long size = jedis.lpush(MESSAGE_KEY, "message:" + count);
                    log.info(producerName + ": 当前未被处理消息条数为:" + size);
                    count++;
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消费者
     */
    private static class Customer extends Thread{
        private String customerName;
        private volatile int count;
        private ShardedJedis jedis;

        public Customer(String name) {
            this.customerName = name;
            jedis = RedisUtil.getShardedJedis();
        }

        @Override
        public void run() {
            while (true) {
                String messages = jedis.rpop(MESSAGE_KEY);
                count++;
                log.info(customerName + " 正在处理消息,消息内容是: " + messages + " 这是第" + count + "条");
            }
        }
    }
}
