package net.gaox.thread.gaoxpool.task;

import net.gaox.thread.gaoxpool.queue.RunnableQueue;

/**
 * <p> 内部任务 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:25
 */
public class InternalTask implements Runnable {

    private final RunnableQueue runnableQueue;

    private volatile boolean running = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                Runnable task = runnableQueue.take();
                task.run();

            } catch (InterruptedException e) {
                running = false;
                break;
            }
        }
    }

    public void stop() {
        this.running = false;
    }

}
