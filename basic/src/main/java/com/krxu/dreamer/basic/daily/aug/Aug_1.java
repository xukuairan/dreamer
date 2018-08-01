package com.krxu.dreamer.basic.daily.aug;

/**
 * @author xukuairan
 * @version [版本号]
 * @date 2018/8/1
 * @description 已知数组 A 内容为1、2、3、4...52，数组 B 内容为 26 个英文字母，
 * 使用两个线程分别输入两个数组，然后使程序运行打印内容为 12a34b56c78e... 的规律，请给出代码实现？
 */
public class Aug_1 {
    private static int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static String[] b = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private static StringBuffer sb = new StringBuffer();
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread ta = new Thread() {
            @Override
            public void run() {
                int wait = 0;
                for (int index = 0; index < a.length; index++) {
                    wait++;
                    synchronized (lock) {
                        sb.append(a[index]);
                        if (wait == 2) {
                            try {
                                lock.notify();
                                lock.wait(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            wait = 0;
                        }
                    }
                }
            }
        };

        Thread tb = new Thread() {
            @Override
            public void run() {
                int wait = 0;
                for (int index = 0; index < b.length; index++) {
                    wait++;
                    synchronized (lock) {
                        sb.append(b[index]);
                        if (wait == 1) {
                            try {
                                lock.notify();
                                lock.wait(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            wait = 0;
                        }
                    }
                }
            }
        };

        ta.start();
        tb.start();
        Thread.sleep(3000);
        System.out.println(sb.toString());
    }
}
