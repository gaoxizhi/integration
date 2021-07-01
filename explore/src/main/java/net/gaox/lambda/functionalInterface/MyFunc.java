package net.gaox.lambda.functionalInterface;

/**
 * <p> 获取参数值：泛型函数式接口 </p>
 *
 * @author gaox·Eric
 * @date 2021/6/11 21:39
 */
@FunctionalInterface
public interface MyFunc<T> {
    /**
     * 获取参数值
     *
     * @param t 参数
     * @return 参数
     */
    T getValue(T t);
}
