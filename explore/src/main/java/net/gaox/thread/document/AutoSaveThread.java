package net.gaox.thread.document;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <p> 模拟自动保存逻辑 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 11:38
 */
public class AutoSaveThread extends Thread {

    private final Document document;

    public AutoSaveThread(Document document) {
        super("DocumentAutoSaveThread");
        this.document = document;
    }

    @Override
    public void run() {
        while (true) {
            try {
                document.save(true);
                TimeUnit.SECONDS.sleep(5);
            } catch (IOException | InterruptedException e) {
                break;
            }
        }

    }

}
