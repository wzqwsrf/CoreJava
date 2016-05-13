package com.wzq.core.jdk;

import java.lang.reflect.*;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date:
 * Description:
 */
public class ReflectDemo {

    public static void main(String[] args) {
        reflect6();
    }

    public static void reflect1() {
        ReflectDemo reflectDemo = new ReflectDemo();
        System.out.println(reflectDemo.getClass().getName());
    }

    public static void reflect2() {
        Class<?> name1 = null;
        Class<?> name2;
        Class<?> name3;
        try {
            name1 = Class.forName("com.wzq.core.jdk.ReflectDemo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        name2 = new ReflectDemo().getClass();
        name3 = ReflectDemo.class;
        System.out.println("name1:" + name1.getName());
        System.out.println("name2:" + name2.getName());
        System.out.println("name3:" + name3.getName());
    }

    public static void reflect3() {
        Class<?> name = null;
        try {
            name = Class.forName("com.wzq.core.jdk.User");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        User user = null;
        try {
            user = (User) name.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        user.setUsername("zhenqing.wang");
        user.setPassword("wangzhenqing");
        System.out.println(user);
    }

    public static void reflect4() {
        Class<?> name = null;
        try {
            name = Class.forName("com.wzq.core.jdk.User");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Constructor<?> constructors[] = name.getConstructors();
        for (int i = 0; i < constructors.length; i++) {
            System.out.println("构造方法：" + constructors[i]);
        }
    }

    public static void reflect5() {
        Class<?> name = null;
        try {
            name = Class.forName("com.wzq.core.jdk.User");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field fields[] = name.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            int modifier = fields[i].getModifiers();
            String priv = Modifier.toString(modifier);
            Class<?> type = fields[i].getType();
            System.out.println(priv + " " + type.getName() + " "
                    + fields[i].getName() + ";");

        }
    }

    public static void reflect6() {
        Class<?> name = null;
        try {
            name = Class.forName("com.wzq.core.jdk.User");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Method method1 = name.getMethod("printUser1", new Class[]{String.class, int.class});
            method1.invoke(name.newInstance(), "zhenqing", 10000);
            Method method2 = name.getMethod("printUser2");
            method2.invoke(name.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

class User {
    String username;
    String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void printUser1(String user, int money){
        System.out.println("user='" + user + '\'' +
                ", money='" + money);
    }

    public void printUser2(){
        System.out.println("Hello, User");
    }
}