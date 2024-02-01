package net.gaox.message.event.file.monitor;

import lombok.extern.slf4j.Slf4j;
import net.gaox.message.event.base.AsyncEventBus;
import net.gaox.message.event.base.EventBus;
import net.gaox.message.event.file.monitor.listener.DirectoryTargetMonitor;
import net.gaox.message.event.file.monitor.listener.FileChangeListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p> 文件目录文件监视 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 17:37
 */
@Slf4j
public class DirectoryMonitorClient {

    private static String targetPath = "/Users/gaox/data";

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

        final EventBus eventBus = new AsyncEventBus("fileChange", executor);
        // 注册文件变动监视器
        eventBus.register(new FileChangeListener());

        DirectoryTargetMonitor monitor = new DirectoryTargetMonitor(eventBus, targetPath);
        monitor.startMonitor();
    }

}
