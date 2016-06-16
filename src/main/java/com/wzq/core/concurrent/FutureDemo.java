package com.wzq.core.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-15 16:26:37
 * Description: Future
 */
public class FutureDemo {

    private static void future1() {
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return System.currentTimeMillis();
            }
        });
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void future2() {
        List<Future> list = new ArrayList<Future>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Future future = executorService.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    Thread.sleep(1000);
                    return Thread.currentThread().getName();
                }
            });
            list.add(future);
        }

        for (Future future : list) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        FutureDemo.future1();
        FutureDemo.future2();
    }
}
