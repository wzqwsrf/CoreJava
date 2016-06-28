package com.wzq.core.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-28 11:08:05
 * Description: Atomic ABA问题
 */
public class AtomicABA {

    private static AtomicInteger atomicInteger = new AtomicInteger(100);

    private static AtomicStampedReference<Integer> atomicReference = new AtomicStampedReference<Integer>(100, 0);

    public void withoutABA() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInteger.compareAndSet(100, 101);
                atomicInteger.compareAndSet(101, 100);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean flag = atomicInteger.compareAndSet(100, 101);
                System.out.println(flag);
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void withABA() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicReference.getStamp();
                atomicReference.compareAndSet(100, 101, stamp, stamp + 1);
                atomicReference.compareAndSet(101, 100, stamp, stamp + 1);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int stamp = atomicReference.getStamp();
                System.out.println(stamp);
                boolean flag = atomicReference.compareAndSet(100, 101,
                        stamp, stamp + 1);
                System.out.println(flag);
            }
        });
        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        AtomicABA atomicABA = new AtomicABA();
        atomicABA.withoutABA();
        atomicABA.withABA();
    }
}
