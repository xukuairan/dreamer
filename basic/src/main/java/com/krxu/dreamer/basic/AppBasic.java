package com.krxu.dreamer.basic;

/**
 * @author xukuairan
 * @version xxx
 * @description 添加类描述
 * @date %date%
 */
public class AppBasic {


    public static void main(String[] args) {
        String a = new String();
        String b = new String();
        System.out.println(a==b);
        System.out.println(a.getClass()==b.getClass());
    }

    private static class Task implements Runnable{


        @Override
        public void run() {
            Thread t = Thread.currentThread();
            System.out.println(t.getName());
            t.start();
        }
    }

}

