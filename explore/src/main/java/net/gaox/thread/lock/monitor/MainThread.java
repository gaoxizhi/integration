package net.gaox.thread.lock.monitor;


import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <p> 设置线程最大处理时间及处理 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/9 12:12
 */
@Slf4j
public class MainThread {
    private Object lock = new Object();

    public static void main(String[] args) {

        MainThread mainThread = new MainThread();

        for (int i = 2; i <= 20; i++) {
            log.info("id = {}, start task!", i);

            MonitorThread monitorThread = new MonitorThread(mainThread);
            Timer timer = new Timer();
            // 每个任务的最大处理时间
            long maxProcessTime = 8 * 1000;
            timer.schedule(monitorThread, maxProcessTime);

            ProcessThread processThread = new ProcessThread(mainThread, i * 1000);
            processThread.start();

            try {
                mainThread.waitLock();
            } catch (InterruptedException e) {
                log.error("id = {}, waitLock error!", i);
            }
            processThread.stop();
            timer.cancel();
            log.info("id = {}, end task!", i);
        }
    }

    /**
     * 阻塞锁
     *
     * @throws InterruptedException 中断异常
     */
    public void waitLock() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }

    /**
     * 唤醒锁
     */
    public void notifyLock() {
        synchronized (lock) {
            lock.notify();
        }
    }

}

/**
 * <p> 监控线程 </p>
 */
@Slf4j
class MonitorThread extends TimerTask {
    private MainThread mt;

    public MonitorThread(MainThread mt) {
        super();
        this.mt = mt;
    }

    @Override
    public void run() {
        log.info("ThreadID: [{}] MonitorThread running!", Thread.currentThread().getId());
        mt.notifyLock();
    }

}

/**
 * <p> 任务处理线程 </p>
 */
@Slf4j
class ProcessThread implements Runnable {
    private MainThread mt;
    private Thread thread;
    private long processTime;

    public ProcessThread(MainThread mt, long processTime) {
        super();
        this.mt = mt;
        this.processTime = processTime;
    }

    private void doSomething() {
        log.info("ThreadID: [{}] >>> Normal Process! processTime={}", thread.getId(), processTime);
        try {
            // 模拟耗时操作
            thread.sleep(processTime);
        } catch (InterruptedException e) {
            log.info("ThreadID: [{}] >>> AbNormal Process! processTime={}", thread.getId(), processTime);
        }
        log.info("ThreadID: [{}] >>> Normal Processed! processTime={}", thread.getId(), processTime);
    }

    public void run() {
        log.info("ThreadID: [{}] >>> starting!", thread.getId());
        doSomething();
        mt.notifyLock();
        log.info("ThreadID: [{}] >>> ending ok!", thread.getId());
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        // 如果任务在正常时间内不能退出，认为产生interrupt,强行地退出 （run方法正常结束）
        thread.interrupt();
        try {
            // 等待线程结束
            thread.join();
        } catch (InterruptedException e) {
            // 重新设置线程中断状态
            Thread.currentThread().interrupt();
            log.error("process thread error!");
        }
        log.info("ThreadID: [{}] >>> stopping end!", thread.getId());
        thread = null;
    }

}
