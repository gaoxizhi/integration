package net.gaox.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p> 中断的标志位 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-19 18:02
 */
@Slf4j
public class InterruptFlagThreadExit {

    public static void main(String[] args) throws InterruptedException {
        ClosedFlag t = new ClosedFlag();
        t.start();
        TimeUnit.SECONDS.sleep(1);
        log.info("System will be shutdown.");
        t.close();
    }

    /**
     * 使用 volatile flag 关闭线程执行
     */
    static class ClosedFlag extends Thread {

        private volatile boolean closed = false;

        @Override
        public void run() {
            log.info("I will start work");
            while (!closed && !isInterrupted()) {
                log.info("i am working.");
            }
            log.info("I will be exiting.");
        }

        public void close() {
            this.closed = true;
            this.interrupt();
        }
    }

}
