package com.wzq.core.sort;

import java.util.Random;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2017-8-31 17:27:52
 * Description: 堆排序实现，本例为最大堆实现
 */
public class HeapSort {

    public void buildMaxHeap(int []array) {
        int len = array.length;
        int index = len / 2 - 1;
        for (int i = index; i >= 0; i--) {
            maxHeapify(array, i, len);
        }
        printArray(array);
    }

    private void maxHeapify(int []array, int index, int len) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int max = index;
        if (left < len && array[max] < array[left]) {
            max = left;
        }
        if (right < len && array[max] < array[right]) {
            max = right;
        }
        if (max != index) {
            int temp = array[index];
            array[index] = array[max];
            array[max] = temp;
            maxHeapify(array, max, len);
        }
    }

    public void heapSort(int []array) {
        buildMaxHeap(array);
        int len = array.length;
        for (int i = len - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            maxHeapify(array, 0, i);
        }
    }

    public void printArray(int []array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int[] array = new int[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            array[i] = random.nextInt(20);
        }
        HeapSort heap = new HeapSort();
        heap.heapSort(array);
        heap.printArray(array);
    }
}
