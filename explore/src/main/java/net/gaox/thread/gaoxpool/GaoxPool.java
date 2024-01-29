package net.gaox.thread.gaoxpool;

import lombok.extern.slf4j.Slf4j;
import net.gaox.thread.gaoxpool.factory.DefaultThreadFactory;
import net.gaox.thread.gaoxpool.factory.ThreadFactory;
import net.gaox.thread.gaoxpool.policy.DenyPolicy;
import net.gaox.thread.gaoxpool.policy.DiscardDenyPolicy;
import net.gaox.thread.gaoxpool.queue.LinkedRunnableQueue;
import net.gaox.thread.gaoxpool.queue.RunnableQueue;
import net.gaox.thread.gaoxpool.task.InternalTask;
import net.gaox.thread.gaoxpool.task.ThreadTask;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * <p> 线程池实现 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:25
 */
@Slf4j
public class GaoxPool extends Thread implements ThreadPool {

    private final int initSize;

    private final int coreSize;

    private final int maxSize;

    private int activeCount;

    private final ThreadFactory threadFactory;

    private final RunnableQueue runnableQueue;

    private volatile boolean isShutdown = false;

    private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();

    private final static DenyPolicy DEFAULT_DENY_POLICY = new DiscardDenyPolicy();

    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    private final long keepAliveTime;

    private final TimeUnit timeUnit;

    public GaoxPool(int initSize, int coreSize, int maxSize, int queueSize) {
        this(initSize, coreSize, maxSize, DEFAULT_THREAD_FACTORY,
                queueSize, DEFAULT_DENY_POLICY, 10, TimeUnit.SECONDS);
    }

    public GaoxPool(int initSize, int coreSize, int maxSize,
                    ThreadFactory threadFactory, int queueSize,
                    DenyPolicy denyPolicy, long keepAliveTime, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        log.info("thread pool create, param: initSize = {}, coreSize = {}, maxSize = {}, queueSize = {}, " +
                        "denyPolicy = {}, keepAliveTime = {} {}.",
                initSize, coreSize, maxSize, queueSize, denyPolicy, keepAliveTime, timeUnit);
        this.init();
    }

    private void init() {
        start();
        for (int i = 0; i < initSize; i++) {
            log.info("create to init.");
            newThread();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (this.isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        this.runnableQueue.offer(runnable);
    }

    private void newThread() {
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        threadQueue.offer(threadTask);
        this.activeCount++;
        log.trace("create a thread.");
        thread.start();
    }

    private void removeThread() {
        ThreadTask threadTask = threadQueue.remove();
        threadTask.getInternalTask().stop();
        log.trace("remove a thread.");
        this.activeCount--;
    }

    @Override
    public void run() {
        while (!isShutdown && !isInterrupted()) {
            try {
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                isShutdown = true;
                break;
            }

            synchronized (this) {
                log.debug("runnableQueue.size = {}, activeCount = {}.", runnableQueue.size(), activeCount);

                // 线程池关闭时，无任何操作
                if (isShutdown) {
                    break;
                }

                // 任务队列非空时，创建线程，数量到 coreSize
                if (runnableQueue.size() > 0 && activeCount < coreSize) {
                    for (int i = initSize; i < coreSize; i++) {
                        log.info("create to coreSize.");
                        newThread();
                    }
                    continue;
                }

                // 任务队列非空时，创建线程，数量到 maxSize
                if (runnableQueue.size() > 0 && activeCount < maxSize) {
                    for (int i = coreSize; i < maxSize; i++) {
                        log.info("create to maxSize.");
                        newThread();
                    }
                }

                // 任务队列空时，删除线程，数量到 coreSize
                if (runnableQueue.size() == 0 && activeCount > coreSize) {
                    for (int i = coreSize; i < maxSize; i++) {
                        log.info("runnableQueue is empty remove overmuch active thread.");
                        removeThread();
                    }
                }
            }
        }
    }

    @Override
    public void shutdown() {
        synchronized (this) {
            if (isShutdown) {
                return;
            }
            isShutdown = true;
            threadQueue.forEach(threadTask -> {
                threadTask.getInternalTask().stop();
                threadTask.getThread().interrupt();
            });
            this.interrupt();
        }
    }

    @Override
    public int getInitSize() {
        if (isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        return this.initSize;
    }

    @Override
    public int getMaxSize() {
        if (isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        return this.maxSize;
    }

    @Override
    public int getCoreSize() {
        if (isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        return this.coreSize;
    }

    @Override
    public int getQueueSize() {
        if (isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        return runnableQueue.size();
    }

    @Override
    public int getActiveCount() {
        synchronized (this) {
            return this.activeCount;
        }
    }

    @Override
    public boolean isShutdown() {
        return this.isShutdown;
    }

}
