package net.gaox.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p> 同步方法的缺陷，不可中断 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-22 12:29
 */
@Slf4j
public class SynchronizedDefect {

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDefect defect = new SynchronizedDefect();
        Thread t1 = new Thread(defect::syncMethod, "T1");
        //make sure the t1 started.
        t1.start();
        TimeUnit.MILLISECONDS.sleep(2);

        Thread t2 = new Thread(defect::syncMethod, "T2");
        //make sure the t2 started.
        t2.start();
        TimeUnit.MILLISECONDS.sleep(2);

        Thread mainThread = Thread.currentThread();

        // 中断 t2
        t2.interrupt();
        log.info("main thread isInterrupted = {}, interrupted = {}, state = {}",
                mainThread.isInterrupted(), Thread.interrupted(), mainThread.getState());
        // t1 不受影响，未中断，状态为休眠 TIMED_WAITING
        log.info("t1 thread isInterrupted = {}, interrupted = {}, state = {}",
                t1.isInterrupted(), t1.interrupted(), t1.getState());
        // t2，被中断，状态还是阻塞状态 BLOCKED
        log.info("t2 thread isInterrupted = {}, interrupted = {}, state = {}",
                t2.isInterrupted(), t2.interrupted(), t2.getState());

        // 中断状态 在上一行调用 t2.interrupted() 时被清除
        log.info("main thread isInterrupted = {}, interrupted = {}, state = {}",
                mainThread.isInterrupted(), Thread.interrupted(), mainThread.getState());
        log.info("t1 thread isInterrupted = {}, interrupted = {}, state = {}",
                t1.isInterrupted(), t1.interrupted(), t1.getState());
        log.info("t2 thread isInterrupted = {}, interrupted = {}, state = {}",
                t2.isInterrupted(), t2.interrupted(), t2.getState());

        log.info("====================main interrupt====================");
        mainThread.interrupt();
        log.info("main thread isInterrupted = {}, interrupted = {}, state = {}",
                mainThread.isInterrupted(), Thread.interrupted(), mainThread.getState());
        // 运行中的线程，可以被中断，也可以清除中断状态
        log.info("main thread isInterrupted = {}, interrupted = {}, state = {}",
                mainThread.isInterrupted(), Thread.interrupted(), mainThread.getState());

    }

    /**
     * 同步方法，休眠 1小时
     */
    public synchronized void syncMethod() {
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
