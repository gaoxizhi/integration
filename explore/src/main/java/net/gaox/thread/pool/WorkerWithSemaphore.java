package net.gaox.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <p> 实现 Runnable 并且保证信号量的工作类 </p>
 *
 * @author gaox·Eric
 * @date 2024/1/17 16:50
 */
@Slf4j
class WorkerWithSemaphore implements Runnable {
    private int id;
    private Semaphore semaphore;

    public WorkerWithSemaphore(int id, Semaphore semaphore) {
        this.id = id;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            log.info("{} 执行任务 {}", Thread.currentThread().getName(), id);
            TimeUnit.MILLISECONDS.sleep(8);
            log.info("{} 完成任务 {}", Thread.currentThread().getName(), id);
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
