package net.gaox.thread.gaoxpool.policy;

import net.gaox.thread.gaoxpool.ThreadPool;

/**
 * <p> 丢弃拒绝策略 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:15
 */
public class DiscardDenyPolicy implements DenyPolicy {

    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
        //do nothing
    }

    @Override
    public String toString() {
        return "DiscardDenyPolicy";
    }

}
