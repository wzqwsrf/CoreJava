package com.wzq.core.Synchronized;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-14 18:26:33
 * Description: 银行
 */
public class Bank6 {
    private AtomicInteger count = new AtomicInteger(0);

    public void addMoney(int money) {
        count.getAndAdd(money);
        System.out.println(System.currentTimeMillis() + "存进" + money);
    }

    public void subMoney(int money) {
        if (count.get() < money) {
            System.out.println("余额不足");
            return;
        }
        count.getAndAdd(-money);
        System.out.println(System.currentTimeMillis() + "取出" + money);
    }

    public void getCurrentMoney() {
        System.out.println("当前余额为" + count);
    }
}
