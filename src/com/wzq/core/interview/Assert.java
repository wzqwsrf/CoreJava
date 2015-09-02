package com.wzq.core.interview;

/**
 * @author: wangzhenqing
 * @date: 2015-09-02 16:15:36
 * @description: 断言
 */
public class Assert {
    public static void main(String[] args) {
        Assert assert1 = new Assert();
        System.out.println(assert1.computerSimpleInterest(10, 2, 2015));
    }

    private int computerSimpleInterest(int principal, float interest, int years) {
        assert (principal > 0);
        return 100;
    }
}
