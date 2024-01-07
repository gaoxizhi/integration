package net.gaox.reflect.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p> 代理处理 Handler </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 00:36
 */
public class ProxyHandler implements InvocationHandler {

    /**
     * 需要使用被代理类的对象进行赋值
     */
    private Object obj;

    public void bind(Object obj) {
        this.obj = obj;
    }

    /**
     * 动态代理步骤2: 通过代理类的对象调用方法a时，动态的去调用被代理类中的同名方法a
     *
     * @param proxy  代理类
     * @param method 代理类对象调用的方法
     * @param args   参数
     * @return returnValue
     * @throws Throwable
     */

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object returnValue = method.invoke(obj, args);

        return returnValue;
    }
}
