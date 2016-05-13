package com.wzq.core.proxy;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 静态代理
 * Description: 2016-05-13 16:24:59
 */
public class AccountProxy {
    public Account account;

    public AccountProxy(Account account) {
        this.account = account;
    }

    public void queryAccount(){
        System.out.println("代理帮忙查询有多少钱======");
        account.queryAccount();
        System.out.println("代理查询结束了哦******");
    }

    public void updateAccount(){
        System.out.println("代理帮忙更新账户余额======");
        account.updateAccount();
        System.out.println("代理更新结束了哦******");
    }
}
