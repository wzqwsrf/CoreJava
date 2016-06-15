package com.wzq.core.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-15 15:56:45
 * Description: BlockingQueue
 */
public class Producer1 implements Runnable {

    private BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);

    public Producer1(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = System.currentTimeMillis() + "";
                blockingQueue.put(message);
                System.out.println("********生产数据" + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
