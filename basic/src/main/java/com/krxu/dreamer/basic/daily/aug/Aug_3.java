package com.krxu.dreamer.basic.daily.aug;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/3
 * @description 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10, 然后是线程3打印11,12,13,14,15.
 * 接着再由线程1打印16,17,18,19,20….以此类推, 直到打印到75
 */
public class Aug_3 {
    public volatile static int START = 0;

    public static Thread[] invokeThreads = new Thread[3];
    public static void main(String[] args) {
        Task task = new Task();

        Thread a = new Thread(task,"t1");
        Thread b = new Thread(task,"t2");
        Thread c = new Thread(task,"t3");
        invokeThreads[0] = a;
        invokeThreads[1] = b;
        invokeThreads[2] = c;

        a.start();
        b.start();
        c.start();
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            printNum();
        }
    }

    public static synchronized void printNum() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName);
        while (START < 75) {
            START++;
            System.out.println(START);
            if(START % 5 == 0){
                try {
                    Aug_3.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Aug_3.class.notify();
                }
            }
        }
    }
}
