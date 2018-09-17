package com.krxu.dreamer.basic.redis;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * @version [版本号]
 * @date 2018/8/28
 * @description redis的事务,实现抢购
 */
@Log4j
public class RedisAffair {
    private static final String REDIS_HOST = "120.79.167.224";
    private static final int REDIS_PORT = 6379;
    private static final String NUM_WATCH_KEY = "1:watch_key";
    private static final String SUCCESS_USER_KEY = "1:success_user";
    private static final String SALE_DETAIL_KEY = "1:sale_detail";

    private static JedisPool pool ;
    static {
        //初始化抢购数据
        pool = new JedisPool(REDIS_HOST, REDIS_PORT);
        Jedis jedis = pool.getResource();
        jedis.set(NUM_WATCH_KEY, "30");
        jedis.del(SUCCESS_USER_KEY, SALE_DETAIL_KEY);
        pool.returnResource(jedis);
    }

    public static void main(String[] args) throws InterruptedException {
        log.info("****************************************");
        Jedis jedis = pool.getResource();
        String wk = jedis.get(NUM_WATCH_KEY);
        pool.returnResource(jedis);
        log.info("watch_key=" + wk);
        log.info("****************************************");

        ExecutorService executor = Executors.newFixedThreadPool(100);
        for(int i = 0 ; i < 200 ; i ++){
            executor.execute(new Task("USER_" + i));
        }
        executor.shutdown();

    }

    /**
     * 抢购任务
     */
    private static class Task implements Runnable{
        private String user;

        public Task(String userName) {
            this.user = userName;
        }

        @Override
        public void run() {
            int num = new Random().nextInt(3) + 1;

            Jedis jedis = pool.getResource();
            try{
                //WATCH命令的作用只是当被监控的键值被修改后阻止之后一个事务的执行
                jedis.watch(NUM_WATCH_KEY);
                Long surplus = Long.valueOf(jedis.get(NUM_WATCH_KEY));

                //就算没有满足超卖条件，事务内有线程抢购成功修改了剩余值导致该线程事务失败，不会导致超卖
                if(num > surplus){
                    log.info("用户：" + user + "下单:" + num + ",剩余:" + surplus);
                    return;
                }

                //开始一个事务
                Transaction transaction = jedis.multi();
                Response<Long> response = transaction.decrBy(NUM_WATCH_KEY, num);
                transaction.sadd(SUCCESS_USER_KEY, user);
                transaction.hset(SALE_DETAIL_KEY, user, num + "");

                // 提交事务，如果此时key被改动了，则返回null
                List<Object> list = transaction.exec();
                if (list != null && !list.isEmpty()) {
                    log.info("用户：" + user + "抢购成功:" + num + ",剩余:" + response.get());
                }
            }catch (Exception ex){
                log.error(ex.getMessage(), ex);
            }finally {
                pool.returnResource(jedis);
            }
        }
    }
}
