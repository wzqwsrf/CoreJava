package com.wzq.core.proxy;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 静态代理
 * Description: 2016-05-13 16:23:00
 */
public class AccountImpl implements Account{
    @Override
    public void queryAccount() {
        System.out.println("查询账户中,账户里有好多钱钱~~~");
    }

    @Override
    public void updateAccount() {
        System.out.println("更新账户中,可以自己随意加钱么~~~");
    }
}
