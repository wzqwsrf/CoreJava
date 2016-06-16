package com.wzq.core.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-16 10:32:58
 * Description: CyclicBarrier
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int num = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num, new Runnable() {
            @Override
            public void run() {
                System.out.println("我宣布，所有的小伙伴们都完成了各自的操作，可以继续了！");
            }
        });
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new WangzqRunnable(cyclicBarrier));
            thread.start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("==========CyclicBarrier重用===========");
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new WangzqRunnable(cyclicBarrier));
            thread.start();
        }
    }

    public static class WangzqRunnable implements Runnable{

        private CyclicBarrier cyclicBarrier;

        public WangzqRunnable(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            int time = new Random().nextInt(1000);
            System.out.println(Thread.currentThread().getName() + "需要" +time + "完成具体事情");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "已完成操作");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程都完成操作了，继续后面的事情吧");
        }
    }
}
