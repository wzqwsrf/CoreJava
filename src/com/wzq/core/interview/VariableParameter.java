package com.wzq.core.interview;

/**
 * @author: wangzhenqing
 * @date: 2015-09-02 16:09:22
 * @description: 可变参数
 */
public class VariableParameter {

    public int sum(int ...numbers){
        int sum = 0;
        for (int num:numbers){
            sum += num;
        }
        return sum;
    }

    public static void main(String[] args) {
        VariableParameter variableParameter = new VariableParameter();
        System.out.println(variableParameter.sum(1,4,5,20));
        System.out.println(variableParameter.sum(1,4,5));
        System.out.println(variableParameter.sum(1));
        System.out.println(variableParameter.sum());
    }
}
