package com.krxu.dreamer.basic.daily.aug;

import org.junit.Test;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/16
 * @description 设计模式：Facade 模式（外观模式）
 */
public class Aug_16 {

    @Test
    public void testFacade() throws InterruptedException {
        Component computer = new ComputerFacade();
        computer.start();
        Thread.sleep(1000);
        computer.shutdown();
    }


    /**
     * 门面类
     */
    private class ComputerFacade implements Component {
        private Component cpu;
        private Component memory;
        private Component disk;

        public ComputerFacade() {
            this.cpu = new Cpu();
            this.memory = new Memory();
            this.disk = new Disk();
        }

        @Override
        public void start() {
            cpu.start();
            memory.start();
            disk.start();
        }

        @Override
        public void shutdown() {
            cpu.shutdown();
            memory.shutdown();
            disk.shutdown();
        }
    }

    private class Cpu implements Component {
        @Override
        public void start() {
            System.out.println("cpu start...");
        }

        @Override
        public void shutdown() {
            System.out.println("cpu shutdown...");
        }
    }

    private class Disk implements Component {
        @Override
        public void start() {
            System.out.println("disk start...");
        }

        @Override
        public void shutdown() {
            System.out.println("disk shutdown...");
        }
    }

    private class Memory implements Component {
        @Override
        public void start() {
            System.out.println("memory start...");
        }

        @Override
        public void shutdown() {
            System.out.println("memory shutdown...");
        }
    }

    private interface Component {
        void start();

        void shutdown();
    }
}
