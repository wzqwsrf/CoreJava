package com.wzq.core.pc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2017-6-20 12:32:47
 * Description: JUC BlockingQueue
 */
public class PCJUC {
    public static void main(String[] args) {
        int size = 10;
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(size);
        JUCProducer producer = new JUCProducer(queue);
        JUCConsumer consumer = new JUCConsumer(queue);
        producer.start();
        consumer.start();
    }
}

class JUCProducer extends Thread {

    private BlockingQueue<String> queue;

    public JUCProducer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                for (int i = 0; i < 100; i++) {
                    queue.put("data-" + i);
                    System.out.println("生产数据:data-" + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class JUCConsumer extends Thread {

    private BlockingQueue<String> queue;

    public JUCConsumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                for (int i = 0; i < 100; i++) {
                    String message = queue.take();
                    System.out.println("======消费数据" + message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
