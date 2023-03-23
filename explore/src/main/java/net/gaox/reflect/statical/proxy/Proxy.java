package net.gaox.reflect.statical.proxy;

/**
 * <p> 代理类 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 00:25
 */

class Proxy implements PaperFactory {
    /**
     * 用被代理类对象进行实例化
     */
    private PaperFactory factory;

    public Proxy(PaperFactory factory) {
        this.factory = factory;
    }

    @Override
    public void produceCloth() {
        System.out.println("造纸厂开始做一些准备工作");

        factory.produceCloth();

        System.out.println("造纸厂做一些后续收尾工作");
    }
}