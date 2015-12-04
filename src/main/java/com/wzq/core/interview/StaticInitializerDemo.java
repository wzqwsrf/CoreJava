package com.wzq.core.interview;

/**
 * @author: wangzhenqing
 * @date: 2015-09-02 16:20:33
 * @description: 静态类初始化
 * static{ 和 }之间的代码被称为静态初始化器。
 * 它只有在第一次加载类时运行。只有静态变量才可以在静态初始化器中进行访问。
 * 虽然创建了三个实例，但静态初始化器只运行一次。
 */
public class StaticInitializerDemo {
    static int count;

    static {
        //This is a static initializers. Run only when Class is first loaded.
        //Only static variables can be accessed
        System.out.println("Static Initializer");
        //i = 6;//COMPILER ERROR
        System.out.println("Count when Static Initializer is run is " + count);
    }

    public static void main(String[] args) {
        StaticInitializerDemo example = new StaticInitializerDemo();
        StaticInitializerDemo example2 = new StaticInitializerDemo();
        StaticInitializerDemo example3 = new StaticInitializerDemo();
    }
}
