package net.gaox.reflect.dynamic.proxy;

import java.lang.reflect.Proxy;

/**
 * <p> 动态的创建一个代理类及其对象 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 00:35
 */

public class ProxyUtil {

    /**
     * 动态代理步骤1: 根据加载到内存中的被代理类，动态的创建一个代理类及其对象
     *
     * @param obj 被代理类的对象
     * @return 代理类的对象
     */
    public static Object getProxyInstance(Object obj) {
        ProxyHandler handler = new ProxyHandler();
        handler.bind(obj);
        Class objClass = obj.getClass();

        return Proxy.newProxyInstance(objClass.getClassLoader(), objClass.getInterfaces(), handler);
    }

}