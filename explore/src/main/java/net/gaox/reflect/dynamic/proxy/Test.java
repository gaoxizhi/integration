package net.gaox.reflect.dynamic.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> 测试 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 00:36
 */

@Slf4j
public class Test {
    public static void main(String[] args) {
        Venus venus = new Venus();
        // 代理类的对象
        Planet planet = (Planet) ProxyUtil.getProxyInstance(venus);

        // 当通过代理类对象调用方法时，会自动的调用被代理类中同名的方法
        String belief = planet.getBelief();
        log.info("planet belief: {}", belief);

        planet.look("曹县");
    }

}