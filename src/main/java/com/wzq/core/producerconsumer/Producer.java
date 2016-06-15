package com.wzq.core.producerconsumer;

import java.util.Date;
import java.util.Vector;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-15 15:13:34
 * Description: 生产者
 */
public class Producer extends Thread {
    private Vector messages = new Vector();
    private static final int MAXQUEUE = 5;

    public void run() {
        while (true) {
            try {
                putMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void putMessage() throws InterruptedException {
        if (messages.size() == MAXQUEUE) {
            wait();
        }
        messages.addElement(new Date().toString());
        System.out.println("put message");
        notify();
    }

    public synchronized String getMessage() throws InterruptedException {
        notify();
        if (messages.size() == 0) {
            wait();
        }
        String message = (String) messages.firstElement();
        messages.remove(message);
        return message;
    }
}
