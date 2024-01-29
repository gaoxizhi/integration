package net.gaox.thread.document;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p> 文档工具类 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 11:17
 */
@Slf4j
public class Document {

    private boolean changed = false;

    private List<String> content = new ArrayList<>();

    private final FileWriter writer;

    private static AutoSaveThread autoSaveThread;

    private Document(String documentPath, String documentName) throws IOException {
        this.writer = new FileWriter(new File(documentPath, documentName), true);
    }

    public static Document create(String documentPath, String documentName) throws IOException {
        Document document = new Document(documentPath, documentName);
        autoSaveThread = new AutoSaveThread(document);
        autoSaveThread.start();
        return document;
    }

    public void edit(String content) {
        synchronized (this) {
            this.content.add(content);
            this.changed = true;
        }
    }

    public void close() throws IOException {
        autoSaveThread.interrupt();
        writer.close();
    }

    public void save(Boolean auto) throws IOException {
        synchronized (this) {
            //balking
            if (!changed) {
                return;
            }
            Boolean autoSave = Optional.ofNullable(auto).orElse(true);
            log.info("{} execute the save action", autoSave ? "Auto" : "Manual");
            for (String cacheLine : content) {
                this.writer.write(cacheLine);
                this.writer.write("\r\n");
            }

            this.writer.flush();
            this.changed = false;
            this.content.clear();
        }
    }

}
