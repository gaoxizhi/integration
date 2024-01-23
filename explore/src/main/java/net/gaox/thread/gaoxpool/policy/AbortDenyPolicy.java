package net.gaox.thread.gaoxpool.policy;

import net.gaox.thread.gaoxpool.ThreadPool;
import net.gaox.thread.gaoxpool.exception.RunnableDenyException;

/**
 * <p> 终止线程池策略 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:17
 */
public class AbortDenyPolicy implements DenyPolicy {

    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
        throw new RunnableDenyException("The runnable " + runnable + " will be abort.");
    }
}