package net.gaox.thread.observable;

/**
 * <p> 事件回调的响应者 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-27 13:15
 */
public interface TaskLifecycle<T> {

    /**
     * 任务开始的回调方法
     *
     * @param thread 回调方法线程
     */
    void onStart(Thread thread);

    /**
     * 任务运行中的回调方法
     *
     * @param thread 回调方法线程
     */
    void onRunning(Thread thread);

    /**
     * 任务运行成功的回调方法
     *
     * @param thread 回调方法线程
     * @param result 运行结果
     */
    void onFinish(Thread thread, T result);

    /**
     * 任务异常的回调方法
     *
     * @param thread 回调方法线程
     * @param e      异常
     */
    void onError(Thread thread, Exception e);

}

