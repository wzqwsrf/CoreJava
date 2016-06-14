package com.wzq.core.thread;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-06 14:09:56
 * Description: Runable Thread
 */
public class ThreadDemo {

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.testThread();
    }

    class ThreadDemo1 extends Thread {
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
        }
    }

    class ThreadDemo2 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
        }
    }

    public void testThread() {
        ThreadDemo1 threadDemo1 = new ThreadDemo1();
        threadDemo1.start();
        ThreadDemo2 threadDemo2 = new ThreadDemo2();
        Thread thread = new Thread(threadDemo2);
        thread.start();
    }

}
