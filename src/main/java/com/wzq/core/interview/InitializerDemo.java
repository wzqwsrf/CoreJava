package com.wzq.core.interview;

/**
 * @author: wangzhenqing
 * @date: 2015-09-02 16:20:33
 * @description: 实例初始化块
 * 每次创建类的实例时，实例初始化器中的代码都会运行。
 */
public class InitializerDemo {
    static int count;
    int i;
    {
        //This is an instance initializers. Run every time an object is created.
        //static and instance variables can be accessed
        System.out.println("Instance Initializer");
        i = 6;
        count = count + 1;
        System.out.println("Count when Instance Initializer is run is " + count);
    }

    public static void main(String[] args) {
        InitializerDemo example = new InitializerDemo();
        InitializerDemo example1 = new InitializerDemo();
        InitializerDemo example2 = new InitializerDemo();
    }
}
