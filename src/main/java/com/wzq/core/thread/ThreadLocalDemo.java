package com.wzq.core.thread;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-13 18:20:00
 * Description: ThreadLocal
 */
public class ThreadLocalDemo {

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    public static class WangzqRunnable implements Runnable {

        @Override
        public void run() {
            threadLocal.set(1);
            System.out.println(Thread.currentThread().getName() + "local值为" + threadLocal.get());
            for (int i = 0; i < 5; i++) {
                threadLocal.set(threadLocal.get() + i);
            }
            System.out.println(Thread.currentThread().getName() + "累加之后local值为" + threadLocal.get());
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new WangzqRunnable());
            thread.start();
        }
    }
}
