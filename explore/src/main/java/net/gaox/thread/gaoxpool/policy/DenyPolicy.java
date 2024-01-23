package net.gaox.thread.gaoxpool.policy;

import net.gaox.thread.gaoxpool.ThreadPool;

/**
 * <p> 拒绝策略接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:12
 */
public interface DenyPolicy {

    void reject(Runnable runnable, ThreadPool threadPool);

}
