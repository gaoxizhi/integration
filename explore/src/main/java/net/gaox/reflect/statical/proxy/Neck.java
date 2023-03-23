package net.gaox.reflect.statical.proxy;

/**
 * <p> 被代理类 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 00:25
 */
public class Neck implements PaperFactory {

    @Override
    public void produceCloth() {
        System.out.println("造纸厂计划生产一批卫生纸");
    }
}
