package com.krxu.dreamer.basic.daily.aug;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/20
 * @description [添加描述]
 * java.util.concurrent.ArrayBlockingQueue 源码
 * @see java.util.concurrent.ArrayBlockingQueue
 */
public class Aug_20 {

    @Test
    public void arrayBlockingQueue(){
        //构造方法有： 1.队列大小 2.队列大小，是否公平竞争锁  3.队列大小，是否公平竞争锁，装入集合
        String[] array = {"qwe","asd","zxc","vbn"};
        List<String> list = Arrays.asList(array);
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(8, true, list);

        //offer()满了返回false    put()满了有等待锁

        //take() peek() poll()

    }
}
