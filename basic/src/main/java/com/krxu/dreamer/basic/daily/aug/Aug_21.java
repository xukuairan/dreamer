package com.krxu.dreamer.basic.daily.aug;

import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author xukuairan
 * @version [版本号]
 * @date 2018/8/21
 * @description [添加描述]
 * @see ConcurrentLinkedQueue 源码
 */
public class Aug_21 {

    @Test
    public void add(){
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();
        queue.add("hello");
        queue.add("world");
    }

}
