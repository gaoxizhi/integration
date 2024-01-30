package net.gaox.util.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p> 自动执行 GC 线程 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 17:20
 */
@Slf4j
public class AutoGcThread extends Thread {

    public AutoGcThread() {
        super("auto-gc");
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                //手动执行 gc 操作
                System.gc();
                log.info("auto GC");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
