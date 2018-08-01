package com.krxu.dreamer.basic;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author xukuairan
 * @version xxx
 * @description 添加类描述
 * @date %date%
 */
public class Application {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {

        new Thread(){
            @Override
            public void run() {
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + countDownLatch.getCount() );
            }
        }.start();

        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + countDownLatch.getCount() );

        countDownLatch.await();

        System.out.println("xxxxxx");


        Thread.sleep(1000);
        new Thread(){
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + countDownLatch.getCount() );
            }
        }.start();
    }

}

