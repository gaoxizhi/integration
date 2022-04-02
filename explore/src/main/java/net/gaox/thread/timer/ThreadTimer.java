package net.gaox.thread.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p> 线程超时 </p>
 *
 * @author gaox·Eric
 * @date 2022/4/2 23:41
 */
public class ThreadTimer {

    public static void main(String[] args) {

        LittleThread littleThread = new LittleThread();

        for (int i = 1; i <= 10; i += 1) {

            System.out.println("start task " + i);
            ProcessThread processThread = new ProcessThread(littleThread, i * 2 * 1000);
            MonitorThread monitorThread = new MonitorThread(littleThread);
            // 每个任务的最大处理时间
            long maxProcessTime = 8 * 100;
            processThread.start();
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(monitorThread, 0, maxProcessTime, TimeUnit.MILLISECONDS);
            try {
                littleThread.waitLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            processThread.stop();
            executorService.shutdown();
            System.out.println("end task " + i);
        }
    }

}
