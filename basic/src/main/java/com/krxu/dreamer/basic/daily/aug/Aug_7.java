package com.krxu.dreamer.basic.daily.aug;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/7
 * @description:
 * java.util.concurrent.CyclicBarrier 栅栏
 * 简单模拟一下对战平台中玩家需要完全准备好了,才能进入游戏的场景。
 */
public class Aug_7 {

    private static int PLAYER_NUM = 5;

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException, TimeoutException {
        CyclicBarrier barrier = new CyclicBarrier(PLAYER_NUM + 1);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < PLAYER_NUM; i++) {
            service.execute(new Player("玩家" + i, barrier));
        }
        barrier.await(30, TimeUnit.SECONDS);
        System.out.println("开局一匹马，输出全靠塔");
        service.shutdown();
    }

    private static class Player implements Runnable{
        private final String name;
        private final CyclicBarrier barrier;
        public Player(String name, CyclicBarrier barrier) {
            this.name = name;
            this.barrier = barrier;
        }
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1 + (new Random().nextInt(3)));
                System.out.println(name + "已准备,等待其他玩家准备...");
                barrier.await();
                TimeUnit.SECONDS.sleep(1 + (new Random().nextInt(3)));
                System.out.println(name + "已加入游戏");
            } catch (InterruptedException e) {
                System.out.println(name + "离开游戏");
            } catch (BrokenBarrierException e) {
                System.out.println(name + "离开游戏");
            }
        }
    }

}
