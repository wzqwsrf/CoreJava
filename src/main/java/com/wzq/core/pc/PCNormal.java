package com.wzq.core.pc;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2017-6-20 11:51:39
 * Description: 生产者消费者模式的基本实现
 */
public class PCNormal {
    public static void main(String[] args) {
        int size = 10;
        WzqBlockingQueue<String> queue = new WzqBlockingQueue<String>(size);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        producer.start();
        consumer.start();
    }
}

class Producer extends Thread {

    private WzqBlockingQueue<String> queue;

    public Producer(WzqBlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                queue.put("data-" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer extends Thread {

    private WzqBlockingQueue<String> queue;

    public Consumer(WzqBlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                String data = queue.take();
                System.out.println(data);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class WzqBlockingQueue<T> {
    private Queue<T> queue = new LinkedList<T>();
    private Object object = new Object();
    private int size;

    public WzqBlockingQueue(int size) {
        this.size = size;
    }

    public void put(T t) throws InterruptedException {
        synchronized (object) {
            while (queue.size() >= size) {
                object.wait();
            }
            queue.add(t);
            System.out.println("+++add data success!+++");
            object.notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (object) {
            while (queue.size() == 0) {
                object.wait();
            }
            T t = queue.poll();
            System.out.println("---take data success!---");
            object.notifyAll();
            return t;
        }
    }
}
