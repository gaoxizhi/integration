package net.gaox.work.thread.v2;

import lombok.Builder;
import net.gaox.thread.future.Future;
import net.gaox.work.thread.base.ActiveFuture;

import java.lang.reflect.Method;

/**
 * <p> 异步处理 消息对象参数等封装 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 14:10
 */
@Builder
public class ActiveMessage {

    /**
     * 接口方法的参数
     */
    private final Object[] objects;

    /**
     * 接口方法
     */
    private final Method method;

    /**
     * 有返回值的方法，会返回 ActiveFuture<?> 类型
     */
    private final ActiveFuture<Object> future;

    /**
     * 具体的 Service 接口
     */
    private final Object service;

    /**
     * ActiveMessage 的方法通过反射的方式调用执行的具体实现
     */
    public void execute() {
        try {
            // 执行接口的方法
            Object result = method.invoke(service, objects);
            // 如果是有返回值的接口方法
            if (future != null) {
                // 通过 get 方法获得最终的结果
                Object realResult = ((Future<?>) result).get();
                // 将结果交给 ActiveFuture，接口方法的线程会得到返回
                future.finish(realResult);
            }
        } catch (Exception e) {
            // 如果发生异常，那么有返回值的方法将会显式地指定结果为 null，无返回值的接口方法则会忽略该异常
            if (future != null) {
                future.finish(null);
            }
        }
    }

}