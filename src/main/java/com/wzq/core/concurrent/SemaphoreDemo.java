package com.wzq.core.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-16 11:05:08
 * Description: Semaphore
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            WangzqRunnable wangzqRunnable = new WangzqRunnable(semaphore);
            pool.execute(wangzqRunnable);
        }
        pool.shutdown();
    }

    public static class WangzqRunnable implements Runnable {

        Semaphore semaphore = new Semaphore(5);

        public WangzqRunnable(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                String name = Thread.currentThread().getName();
                int time = new Random().nextInt(1000);
                semaphore.acquire();
                System.out.println("Accessing: " + name);
                Thread.sleep(time);
                System.out.println(name + "执行" + time + ", 释放许可");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
