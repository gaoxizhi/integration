package net.gaox.thread.observable;

/**
 * <p> 事件回调的响应者 默认空实现 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-27 13:22
 */
public class EmptyLifecycle<T> implements TaskLifecycle<T> {

    @Override
    public void onStart(Thread thread) {
        //do nothing
    }

    @Override
    public void onRunning(Thread thread) {
        //do nothing
    }

    @Override
    public void onFinish(Thread thread, T result) {
        //do nothing
    }

    @Override
    public void onError(Thread thread, Exception e) {
        //do nothing
    }
}
