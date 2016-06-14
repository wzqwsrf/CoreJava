package com.wzq.core.Synchronized;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-14 18:29:56
 * Description: Main函数
 */
public class Main {
    public static void main(String[] args) {
        final Bank bank = new Bank();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    bank.addMoney(100);
                    bank.getCurrentMoney();
                    System.out.println("=========");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    bank.subMoney(200);
                    bank.getCurrentMoney();
                    System.out.println("×××××××××");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread1.start();
        thread2.start();
    }
}
