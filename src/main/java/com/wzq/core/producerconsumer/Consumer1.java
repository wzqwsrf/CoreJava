package com.wzq.core.producerconsumer;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-15 15:53:17
 * Description: BlockingQueue
 */
public class Consumer1 implements Runnable {
    private BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);

    public Consumer1(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = blockingQueue.take();
                System.out.println("======消费数据" + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
