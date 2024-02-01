package net.gaox.work.thread.base;

import net.gaox.thread.future.FutureTask;

/**
 * <p> 异步执行订单 Future </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 12:05
 */
public class ActiveFuture<T> extends FutureTask<T> {

    /**
     * 通知完成
     *
     * @param result 执行结果
     */
    @Override
    public void finish(T result) {
        super.finish(result);
    }

}
