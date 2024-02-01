package net.gaox.message.event.base;

/**
 * <p> 时间异常处理接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 12:05
 */
public interface EventExceptionHandler {

    /**
     * EventBus 会将方法的调用交给 Runnable 接口去执行
     * Runnable 接口不能抛出 checked 异常信息，并且在每一个 subscribe 方法中，也不允许将异常抛出
     * 从而影响 EventBus 对后续 Subscriber 进行消息推送，但是异常信息又不能被忽略掉
     * 注册一个异常回调接口就可以知道在进行消息广播推送时都发生了什么
     *
     * @param cause   异常信息
     * @param context 事件上下文
     */
    void handle(Throwable cause, EventContext context);

}
