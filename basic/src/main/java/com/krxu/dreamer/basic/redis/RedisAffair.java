package com.krxu.dreamer.basic.redis;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
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

    private static final String KEY = "watch_key";
    private static final String REDIS_HOST = "127.0.0.1";
    private static final int REDIS_PORT = 6379;

    static {
        //初始化抢购数据
        Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        jedis.set(KEY, "0");
        jedis.del("setsucc", "setfail");
        jedis.close();
    }

    public static void main(String[] args) throws InterruptedException {
        log.info("****************************************");
        Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        String wk = jedis.get(KEY);
        boolean setsucc = jedis.exists("setsucc");
        boolean setfail = jedis.exists("setfail");
        jedis.close();
        log.info("watch_key=" + wk);
        log.info("setsucc=" + setsucc + ", setfail=" + setfail);
        log.info("****************************************");


        ExecutorService executor = Executors.newFixedThreadPool(100);
        for(int i = 0 ; i < 20 ; i ++){
            Thread.sleep(100);
            executor.execute(new Task());
        }
        executor.shutdown();

    }

    /**
     * 抢购任务
     */
    private static class Task implements Runnable{
        private Jedis jedis ;

        public Task( ) {
            this.jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        }

        @Override
        public void run() {
            String user = Thread.currentThread().getName();
            String re = jedis.get(KEY);
            Long sold = Long.valueOf(re);

            if(sold > 10){
                jedis.sadd("setfail", user);
                return;
            }

            try{
                jedis.watch(KEY);
                //开始一个事务
                Transaction transaction = jedis.multi();
                transaction.incr(KEY);
                // 提交事务，如果此时key被改动了，则返回null
                List<Object> list = transaction.exec();

                if (list != null && !list.isEmpty()) {
                    log.info("用户：" + user + "抢购成功," + sold);
                    jedis.sadd("setsucc", user);
                }else {
                    jedis.sadd("setfail", user);
                }
            }catch (Exception ex){
                log.error(ex.getMessage(), ex);
            }finally {
                jedis.close();
            }
        }
    }

}
