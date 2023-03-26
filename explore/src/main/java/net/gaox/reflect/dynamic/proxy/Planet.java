package net.gaox.reflect.dynamic.proxy;

/**
 * <p> 行星 接口 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 00:33
 */
public interface Planet {
    /**
     * 信条
     *
     * @return 信条
     */
    String getBelief();

    /**
     * 观察
     *
     * @param place 地点
     */
    void look(String place);
}
