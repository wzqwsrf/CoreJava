package com.wzq.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 动态代理
 * Description: 2016-05-13 16:31:42
 */
public class AccountProxyFactory implements InvocationHandler{

    private Object target;

    public Object bind(Object target){
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//      这里可以增加一些额外操作，其实就是AOP咯
        return method.invoke(target, args);
    }
}