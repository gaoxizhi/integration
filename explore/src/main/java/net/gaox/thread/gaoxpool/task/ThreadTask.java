package net.gaox.thread.gaoxpool.task;

import lombok.Data;

/**
 * <p> 线程池的线程任务 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 14:37
 */
@Data
public class ThreadTask {

    Thread thread;

    InternalTask internalTask;

    public ThreadTask(Thread thread, InternalTask internalTask) {
        this.thread = thread;
        this.internalTask = internalTask;
    }

}
