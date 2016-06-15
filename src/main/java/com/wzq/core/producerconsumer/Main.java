package com.wzq.core.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-15 16:00:01
 * Description: 测试类
 */
public class Main {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);
        Producer1 producer1 = new Producer1(blockingQueue);
        Consumer1 consumer1 = new Consumer1(blockingQueue);
        Consumer1 consumer2 = new Consumer1(blockingQueue);
        new Thread(producer1).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}
