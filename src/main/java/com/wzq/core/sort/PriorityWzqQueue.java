package com.wzq.core.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2017-9-1 12:06:42
 * Description: PriorityQueue实现，siftUp and siftDown 可以扩展成为泛型 etc
 */
public class PriorityWzqQueue {
    List<Integer> list;

    public PriorityWzqQueue() {
        list = new ArrayList<Integer>();
    }

    public void offer(int val) {
        list.add(val);
        siftUp(list.size() - 1);
    }

    public int poll() {
        int size = list.size();
        swap(0, size - 1);
        int num = list.get(size - 1);
        list.remove(size - 1);
        size -= 1;
        siftDown(0, size);
        return num;
    }

    public void siftUp(int index) {
        int parent = (index - 1) / 2;
        int max = index;
        if (parent >= 0 && list.get(index) < list.get(parent)) {
            max = parent;
        }
        if (max != index) {
            swap(index, max);
            siftUp(max);
        }
    }

    public void siftDown(int index, int len) {
        int left = 2 * index + 1;
        int right = left + 1;
        int max = index;
        if (left < len && list.get(max) > list.get(left)) {
            max = left;
        }
        if (right < len && list.get(max) > list.get(right)) {
            max = right;
        }
        if (max != index) {
            swap(index, max);
            siftDown(max, len);
        }
    }

    private void swap(int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static void main(String[] args) {
        PriorityWzqQueue queue = new PriorityWzqQueue();
        queue.offer(10);
        queue.offer(6);
        System.out.println(queue.poll());
        queue.offer(11);
        queue.offer(-2);
        System.out.println(queue.poll());
        queue.offer(9);
        queue.offer(10);
        System.out.println(queue.poll());
        queue.offer(-2);
        System.out.println(queue.poll());
        queue.offer(12);
        System.out.println(queue.poll());

        System.out.println("===================");
        Queue<Integer> q = new PriorityQueue<Integer>();
        q.offer(10);
        q.offer(6);
        System.out.println(q.poll());
        q.offer(11);
        q.offer(-2);
        System.out.println(q.poll());
        q.offer(9);
        q.offer(10);
        System.out.println(q.poll());
        q.offer(-2);
        System.out.println(q.poll());
        q.offer(12);
        System.out.println(q.poll());
    }
}
