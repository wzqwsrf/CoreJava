package com.wzq.core.interview;

import java.util.Random;

/**
 * @author: wangzhenqing
 * @date: 2017-11-09 16:26:21
 * @description: 快速排序
 * 这是在baidu蜂巢面试的时候问的，当时写的有点小问题，回来重新写了一下
 * 其实快速排序真的就是挖坑，然后填坑
 * 从右往左 找到第一个比index小的数，挖坑，把该数填充到low位置
 * 从左往右 找到第一个比index大的数，挖坑，把该数填充到high位置
 * 循环往复 直到low >= high
 * 会不会忘记啊。。。
 */
public class QuickSort {
    public void quickSort(int[] nums, int low, int high) {
        if (low < high) {
            int index = actualSort(nums, low, high);
            quickSort(nums, low, index);
            quickSort(nums, index + 1, high);
        }
    }

    private int actualSort(int[] nums, int low, int high) {
        int temp = nums[low];
        while (low < high) {
            while (low < high && nums[high] >= temp) {
                high--;
            }
            nums[low] = nums[high];
            while (low < high && nums[low] <= temp) {
                low++;
            }
            nums[high] = nums[low];
        }
        nums[low] = temp;
        return low;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int len = 10;
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = random.nextInt(20);
        }
        new QuickSort().quickSort(nums, 0, len - 1);
        for (int i = 0; i < len; i++) {
            System.out.printf(nums[i] + ", ");
        }
        System.out.println();
    }
}
