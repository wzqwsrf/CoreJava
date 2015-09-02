package com.wzq.core.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: wangzhenqing
 * @date: 2015-09-02 16:34:15
 * @description: 线程池
 */
public class ThreadPool {
    private static int COUNT = 10000;

    public void testThreadPool() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        long bg = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            Runnable command = new TestRunnable(countDownLatch);
            executorService.execute(command);
        }
        countDownLatch.await();
        System.out.println("testThreadPool:" + (System.currentTimeMillis() - bg));
    }

    private class TestRunnable implements Runnable {
        private final CountDownLatch countDownLatch;

        TestRunnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            countDownLatch.countDown();
        }
    }

    public void testNewThread() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);
        long bg = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            Runnable command = new TestRunnable(countDownLatch);
            Thread thread = new Thread(command);
            thread.start();
        }
        countDownLatch.await();
        System.out.println("testNewThread:" + (System.currentTimeMillis() - bg));
    }

    public static void main(String[] args) throws Exception {
        ThreadPool pool = new ThreadPool();
        pool.testThreadPool();
        pool.testNewThread();
    }
}
