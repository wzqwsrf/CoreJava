package com.wzq.core.Synchronized;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-14 18:42:27
 * Description: synchronized 银行
 */
public class Bank2 {
    private int count = 0;

    public void addMoney(int money) {
        synchronized (this) {
            count += money;
        }
        System.out.println(System.currentTimeMillis() + "存进" + money);
    }

    public synchronized void subMoney(int money) {
        synchronized (this) {
            if (count < money) {
                System.out.println("余额不足");
                return;
            }
            count -= money;
        }
        System.out.println(System.currentTimeMillis() + "取出" + money);
    }

    public void getCurrentMoney() {
        System.out.println("当前余额为" + count);
    }
}
