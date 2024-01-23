package net.gaox.thread.gaoxpool.policy;

import net.gaox.thread.gaoxpool.ThreadPool;

/**
 * <p> 在提交任务的线程执行 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:18
 */
public class RunnerDenyPolicy implements DenyPolicy {

    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
        if (!threadPool.isShutdown()) {
            runnable.run();
        }
    }

}
