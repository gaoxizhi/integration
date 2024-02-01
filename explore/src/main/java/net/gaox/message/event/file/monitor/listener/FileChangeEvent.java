package net.gaox.message.event.file.monitor.listener;

import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * <p> 文件变动信息 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 17:22
 */
@Getter
public class FileChangeEvent {

    private final Path path;

    private final WatchEvent.Kind<?> kind;

    public FileChangeEvent(Path path, WatchEvent.Kind<?> kind) {
        this.path = path;
        this.kind = kind;
    }

}
