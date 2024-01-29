package net.gaox.thread.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> Future 任务执行逻辑实现 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 09:51
 */
@Slf4j
public class FutureServiceImpl<IN, OUT> implements FutureService<IN, OUT> {

    /**
     * 为执行的线程指定名字前缀
     */
    private final static String FUTURE_THREAD_PREFIX = "FUTURE-";

    private final AtomicInteger nextCounter = new AtomicInteger(0);

    private String getNextName() {
        return FUTURE_THREAD_PREFIX + nextCounter.getAndIncrement();
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        final FutureTask<Void> future = new FutureTask<>();
        new Thread(() -> {
            runnable.run();
            // 任务执行结束之后将 null 作为结果传给 future
            future.finish(null);
        }, getNextName()).start();

        return future;
    }

    @Override
    public Future<OUT> submit(Task<IN, OUT> task, IN input, Callback<OUT> callback) {
        final FutureTask<OUT> future = new FutureTask<>();
        new Thread(() -> {
            OUT result = task.get(input);
            // 任务执行结束之后，将真实的结果通过finish方法传递给future
            future.finish(result);
            if (null != callback)
                callback.call(result);
        }, getNextName()).start();

        return future;
    }

}
