package com.wzq.core.proxy;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 静态代理测试类
 * Description: 2016-05-13 16:27:55
 */
public class Main {
    public static void main(String[] args) {
        proxy1();
        proxy2();
    }

    public static void proxy1(){
        Account account = new AccountImpl();
        AccountProxy accountProxy = new AccountProxy(account);
        accountProxy.queryAccount();
        accountProxy.updateAccount();
    }

    public static void proxy2(){
        AccountProxyFactory accountProxyFactory = new AccountProxyFactory();
        Account account = (Account) accountProxyFactory.bind(new AccountImpl());
        account.queryAccount();
        account.updateAccount();
    }
}
