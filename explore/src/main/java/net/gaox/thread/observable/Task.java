package net.gaox.thread.observable;

/**
 * <p> 任务执行器 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-27 13:11
 */

@FunctionalInterface
public interface Task<T> {

    T call();

}
