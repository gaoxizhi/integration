package net.gaox.thread.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p> ScheduledExecutorService 替代 Timer 定时器 </p>
 *
 * @author gaox·Eric
 * @date 2022/4/3 00:33
 */
public class ScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        scheduledThreadPool.schedule(() -> System.out.println("延迟三秒后执行一次"), 3, TimeUnit.SECONDS);
        scheduledThreadPool.scheduleAtFixedRate(() -> System.out.println("延迟 1 秒后每三秒执行一次"), 1, 3, TimeUnit.SECONDS);
    }

}
