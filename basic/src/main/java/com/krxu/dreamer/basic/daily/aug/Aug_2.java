package com.krxu.dreamer.basic.daily.aug;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author xukuairan
 * @version [版本号]
 * @date 2018/8/2
 * @description
 * 有返回值的线程的创建
 */
public class Aug_2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable callable = new MyCallable("xukuairan");
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(callable);
        String result = future.get();

        System.out.println(result);

        pool.shutdown();
    }

    private static class MyCallable implements Callable<String>{

        private String name;

        public MyCallable(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            return name + "_" + UUID.randomUUID().toString().replace("-","");
        }
    }


}
