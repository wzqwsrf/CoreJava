package com.wzq.core.Synchronized;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-14 18:26:33
 * Description: ThreadLocal银行
 */
public class Bank5 {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            // TODO Auto-generated method stub
            return 0;
        }
    };

    public void addMoney(int money) {
        threadLocal.set(threadLocal.get() + money);
        System.out.println(System.currentTimeMillis() + "存进" + money);
    }

    public void subMoney(int money) {
        if (threadLocal.get() < money) {
            System.out.println("余额不足");
            return;
        }
        threadLocal.set(threadLocal.get() - money);
        System.out.println(System.currentTimeMillis() + "取出" + money);
    }

    public void getCurrentMoney() {
        System.out.println("当前余额为" + threadLocal.get());
    }
}
