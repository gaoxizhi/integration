package net.gaox.lambda.functional.interfaces;

/**
 * <p> 获取数值：函数式接口 </p>
 *
 * @author gaox·Eric
 * @date 2021/6/11 21:38
 */
@FunctionalInterface
public interface MyNumber {
    /**
     * 获取数值
     *
     * @return double
     */
    Double getValue();
}
