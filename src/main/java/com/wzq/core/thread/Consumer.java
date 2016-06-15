package com.wzq.core.thread;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-15 15:13:44
 * Description: 消费者
 */
public class Consumer extends Thread {
    private Producer producer;

    public Consumer(Producer producer) {
        this.producer = producer;
    }

    public void run() {
        try {
            while (true) {
                String message = producer.getMessage();
                System.out.println("Got message: " + message);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Producer p = new Producer();
        p.start();
        Consumer consumer = new Consumer(p);
        consumer.start();
    }
}
