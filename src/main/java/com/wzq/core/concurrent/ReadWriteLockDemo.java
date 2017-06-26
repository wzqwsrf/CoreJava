package main.java.com.wzq.core.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2017-6-26 19:14:22
 * Description: ReentrantReadWriteLock
 */
public class ReadWriteLockDemo {
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();

    public void read() {
        try {
            rwlock.readLock().lock();
            System.out.println(System.currentTimeMillis() + "read data");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwlock.readLock().unlock();
        }
    }

    public void write() {
        try {
            rwlock.writeLock().lock();
            System.out.println(System.currentTimeMillis() + "write data");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        ExecutorService excutor = Executors.newCachedThreadPool();
//        for (int i = 0; i < 3; i++) {
//            excutor.submit(new Runnable() {
//                @Override
//                public void run() {
//                    readWriteLockDemo.write();
//                }
//            });
//        }

        for (int i = 0; i < 3; i++) {
            excutor.submit(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.read();
                }
            });
        }
    }
}
