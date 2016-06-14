package com.wzq.core.Synchronized;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-14 18:42:27
 * Description: volatile 银行
 */
public class Bank4 {
    private int count = 0;

    private Lock lock = new ReentrantLock();

    public void addMoney(int money) {
        lock.lock();
        try {
            count += money;
            System.out.println(System.currentTimeMillis() + "存进" + money);
        } finally {
            lock.unlock();
        }
    }

    public synchronized void subMoney(int money) {
        lock.lock();
        try {
            if (count < money) {
                System.out.println("余额不足");
                return;
            }
            count -= money;
            System.out.println(System.currentTimeMillis() + "取出" + money);
        } finally {
            lock.unlock();
        }
    }

    public void getCurrentMoney() {
        System.out.println("当前余额为" + count);
    }
}
