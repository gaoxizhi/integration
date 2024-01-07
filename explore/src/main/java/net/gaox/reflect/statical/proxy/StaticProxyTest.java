package net.gaox.reflect.statical.proxy;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 00:25
 */

public class StaticProxyTest {
    public static void main(String[] args) {
        // 创建被代理类的对象
        PaperFactory word = new Neck();

        // 创建代理类的对象
        PaperFactory proxyPaperFactory = new Proxy(word);

        proxyPaperFactory.produceCloth();
    }
}