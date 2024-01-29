package net.gaox.thread.future;

/**
 * <p> Future 接口和工具方法 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 09:12
 */
public interface FutureService<IN, OUT> {

    /**
     * 提交不需要返回值的任务，Future.get 方法返回的将会是 null
     *
     * @param runnable 任务
     * @return 结果凭证
     */
    Future<?> submit(Runnable runnable);

    /**
     * 提交需要返回值的任务，其中 Task 接口代替了 Runnable 接口
     *
     * @param task     执行任务
     * @param input    输入
     * @param callback 回调方法
     * @return
     */
    Future<OUT> submit(Task<IN, OUT> task, IN input, Callback<OUT> callback);

    /**
     * 使用静态方法创建一个 FutureService 的实现
     *
     * @param <IN>  入参
     * @param <OUT> 结果
     * @return FutureService 实现
     */
    static <IN, OUT> FutureService<IN, OUT> newService() {
        return new FutureServiceImpl<>();
    }

}
