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

    /**
     * 文档是否发生改变的标志，默认情况下为 false，当有新的内容被编辑的时候将被修改为 true
     */
    private boolean changed = false;

    /**
     * 一次需要保存的内容，可以将其理解为内容缓存
     */
    private List<String> content = new ArrayList<>();

    private final FileWriter writer;

    /**
     * 自动保存文档的线程
     */
    private static AutoSaveThread autoSaveThread;

    /**
     * 创建文档
     *
     * @param documentPath 文档路径
     * @param documentName 文档名称
     * @throws IOException ioe
     */
    private Document(String documentPath, String documentName) throws IOException {
        this.writer = new FileWriter(new File(documentPath, documentName), true);
    }

    /**
     * 创建文档的静态方法，顺便启动自动保存文档的线程
     *
     * @param documentPath 文档路径
     * @param documentName 文档名称
     * @throws IOException ioe
     */
    public static Document create(String documentPath, String documentName) throws IOException {
        Document document = new Document(documentPath, documentName);
        // 启动自动保存文档的线程，在固定时间里执行一次文档保存动作
        autoSaveThread = new AutoSaveThread(document);
        autoSaveThread.start();
        return document;
    }

    /**
     * 文档的编辑 往队列中提交字符串内容
     * edit 和 save 使用方法同步，防止当文档在保存的过程中如果遇到新的内容被编辑时引起的共享资源冲突问题
     *
     * @param content 字符串内容
     */
    public void edit(String content) {
        synchronized (this) {
            this.content.add(content);
            this.changed = true;
        }
    }

    /**
     * 关闭文档
     *
     * @throws IOException ioe
     */
    public void close() throws IOException {
        // 中断自动保存线程
        autoSaveThread.interrupt();
        // 关闭 writer 释放资源
        writer.close();
    }

    /**
     * 文档保存
     *
     * @param auto 是否自动保存
     * @throws IOException ioe
     */
    public void save(Boolean auto) throws IOException {
        synchronized (this) {
            // balking，如果文档已经被保存了，则直接返回
            // 其他场景：比如在系统资源的加载或者某些数据的初始化时，在整个系统中声明周期中资源可能只被加载一次，再次进入时直接 return
            if (!changed) {
                return;
            }
            // 保存记录日志
            Boolean autoSave = Optional.ofNullable(auto).orElse(true);
            log.info("{} execute the save action", autoSave ? "Auto" : "Manual");

            // 将内容写入文档中
            for (String cacheLine : content) {
                this.writer.write(cacheLine);
                this.writer.write("\r\n");
            }

            // 保存完毕后，重置文档状态
            this.writer.flush();
            this.changed = false;
            this.content.clear();
        }
    }

}
