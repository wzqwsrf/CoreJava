package com.wzq.core.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author: wangzhenqing
 * @date: 2017-11-09 15:41:36
 * @description: 线程池 Callable以及Future用法
 * 这是在baidu蜂巢面试的时候问的，当时写的有点小问题，回来重新写了一下
 * 业务逻辑是用多线程计算List中相邻两项的和并输出
 * 然而面试并没有过
 */
public class ThreadPoolDemo {

    public List<Integer> getTwoSumThreadList(List<Integer> nums) {
        long startTime = System.currentTimeMillis();
        int size = nums.size();
        ExecutorService service = Executors.newFixedThreadPool(50);
        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
        for (int i = 0; i < size / 2; i++) {
            Future<Integer> future = service.submit(new AddCallable(nums.get(i), nums.get(i + 1)));
            futureList.add(future);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size / 2; i++) {
            try {
                list.add(futureList.get(i).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("time = " + (endTime - startTime));
        service.shutdownNow();
        return list;
    }

    private boolean compareList(List<Integer> list1, List<Integer> list2) {
        int size1 = list1.size();
        int size2 = list2.size();
        if (size1 != size2) {
            return false;
        }
        size1--;
        size2--;
        while (size1 >= 0 && size2 >= 0) {
            if (!list1.get(size1).equals(list2.get(size2))) {
                return false;
            }
            size1--;
            size2--;
        }
        return true;
    }

    public List<Integer> getTwoSumList(List<Integer> nums) {
        long startTime = System.currentTimeMillis();
        int size = nums.size();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size / 2; i++) {
            list.add(nums.get(i) + nums.get(i + 1));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("time = " + (endTime - startTime));
        return list;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int size = 10000000;
        List<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            nums.add(random.nextInt(100));
        }
        List<Integer> list1 = new ThreadPoolDemo().getTwoSumThreadList(nums);
        List<Integer> list2 = new ThreadPoolDemo().getTwoSumList(nums);
        boolean flag = new ThreadPoolDemo().compareList(list1, list2);
        System.out.println(flag);
    }
}

class AddCallable implements Callable<Integer> {

    int num1;
    int num2;

    public AddCallable(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public Integer call() throws Exception {
        return num1 + num2;
    }
}
