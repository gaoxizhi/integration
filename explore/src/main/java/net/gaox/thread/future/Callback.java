package net.gaox.thread.future;

/**
 * <p> 回调方法接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 09:25
 */
@FunctionalInterface
public interface Callback<T> {

    void call(T t);

}
