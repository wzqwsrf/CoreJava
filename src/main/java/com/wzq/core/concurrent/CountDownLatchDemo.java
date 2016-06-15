package com.wzq.core.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-15 18:39:17
 * Description: CountDownLatch
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            WangzqRunnable wangzqRunnable = new WangzqRunnable(countDownLatch);
            Thread thread = new Thread(wangzqRunnable);
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束");
    }

    public static class WangzqRunnable implements Runnable{
        private CountDownLatch countDownLatch;
        public WangzqRunnable(CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            countDownLatch.countDown();
        }
    }
}
