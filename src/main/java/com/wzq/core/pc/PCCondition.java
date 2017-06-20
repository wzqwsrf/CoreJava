package com.wzq.core.pc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2017-6-20 12:23:47
 * Description: 生产者消费者模式 JUC Lock condition
 */
public class PCCondition {
    public static void main(String[] args) {
        int size = 10;
        ConditionBlockingQueue<String> queue = new ConditionBlockingQueue<String>(size);
        ConditionProducer producer = new ConditionProducer(queue);
        ConditionConsumer consumer = new ConditionConsumer(queue);
        producer.start();
        consumer.start();
    }
}

class ConditionProducer extends Thread {

    private ConditionBlockingQueue<String> queue;

    public ConditionProducer(ConditionBlockingQueue<String> queue) {
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

class ConditionConsumer extends Thread {

    private ConditionBlockingQueue<String> queue;

    public ConditionConsumer(ConditionBlockingQueue<String> queue) {
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

class ConditionBlockingQueue<T> {
    private Queue<T> queue = new LinkedList<T>();
    private Lock lock = new ReentrantLock();
    private Condition putCondition = lock.newCondition();
    private Condition takeCondition = lock.newCondition();
    private int size;

    public ConditionBlockingQueue(int size) {
        this.size = size;
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() >= size){
                putCondition.await();
            }
            queue.add(t);
            System.out.println("+++add data success!+++");
            takeCondition.signal();
        }finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == 0) {
                takeCondition.await();
            }
            T t = queue.poll();
            System.out.println("---take data success!---");
            putCondition.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }
}
