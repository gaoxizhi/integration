package net.gaox.message.event.file.monitor.listener;

import lombok.extern.slf4j.Slf4j;
import net.gaox.message.event.base.Subscribe;

import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

/**
 * <p> 监听文件变化 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 17:31
 */
@Slf4j
public class FileChangeListener {

    /**
     * 监听文件变化
     *
     * @param event 变化事件
     */
    @Subscribe("fileChange")
    public void onChange(FileChangeEvent event) {
        log.info("文件:[{}] 被 [{}]", event.getPath(), change(event.getKind()));
    }

    /**
     * 根据事件类型，返回事件类型
     *
     * @param eventKind 事件类型
     * @return 事件类型
     */
    private static String change(WatchEvent.Kind<?> eventKind) {
        String kind;
        if (eventKind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
            kind = "创建";
        } else if (eventKind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
            kind = "删除";
        } else if (eventKind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
            kind = "修改";
        } else {
            kind = "未知";
        }
        return kind;
    }

}
