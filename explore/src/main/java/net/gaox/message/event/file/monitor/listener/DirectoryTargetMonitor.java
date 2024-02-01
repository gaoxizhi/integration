package net.gaox.message.event.file.monitor.listener;

import lombok.extern.slf4j.Slf4j;
import net.gaox.message.event.base.EventBus;

import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p> 目录监视器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 16:49
 */
@Slf4j
public class DirectoryTargetMonitor {
    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath) {
        this(eventBus, targetPath, "");
    }

    // 构造Monitor的时候需要传入EventBus以及需要监控的目录
    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String... morePaths) {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePaths);
    }

    public void startMonitor() throws Exception {
        this.watchService = FileSystems.getDefault().newWatchService();
        // 为路径注册感兴趣的事件
        // 在创建 WatchService之后将文件的修改、删除、创建等注册给了WatchService
        this.path.register(watchService,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_CREATE);
        log.info("The directory [{}] is monitoring... ", path);

        this.start = true;
        while (start) {
            WatchKey watchKey = null;
            try {
                // 当有事件发生时会返回对应的 WatchKey
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event -> {
                    // 事件类型
                    WatchEvent.Kind<?> kind = event.kind();
                    Path path = (Path) event.context();
                    // 变化的文件 Path
                    Path child = DirectoryTargetMonitor.this.path.resolve(path);

                    // 提交 FileChangeEvent 到 EventBus
                    if (isWatchFile(child)) {
                        eventBus.post(new FileChangeEvent(child, kind));
                    }
                });
            } catch (Exception e) {
                log.error("The directory [{}] monitor error.", path, e);
                this.start = false;
            } finally {
                if (watchKey != null) {
                    watchKey.reset();
                }
            }
        }
    }

    public void stopMonitor() throws Exception {
        log.info("The directory [{}] monitor will be stop... ", path);
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
        log.info("The directory [{}] monitor will be stop done.", path);
    }

    /**
     * 是否需要监听的文件
     *
     * @param path 文件路径
     * @return 是否需要监听
     */
    private static boolean isWatchFile(Path path) {
        List<String> ignoreFileList = Arrays.asList("ignore", ".DS_Store");
        boolean ignoreFile = Optional.ofNullable(path.getFileName())
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(s -> ignoreFileList.stream().anyMatch(s::contains))
                .isPresent();
        return !ignoreFile;
    }

}
