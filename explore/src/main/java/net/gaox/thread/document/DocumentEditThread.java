package net.gaox.thread.document;

import java.io.IOException;
import java.util.Scanner;

/**
 * <p> 模拟编辑文档的线程 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 11:59
 */
public class DocumentEditThread extends Thread {

    private final String documentPath;

    private final String documentName;

    private final Scanner scanner = new Scanner(System.in);

    public DocumentEditThread(String documentPath, String documentName) {
        super("DocumentEditThread");
        this.documentPath = documentPath;
        this.documentName = documentName;
    }

    @Override
    public void run() {
        // 最大编辑未保存次数，默认为5次
        int times = 0;
        try {
            Document document = Document.create(documentPath, documentName);
            while (true) {
                String text = scanner.next();
                if ("quit".equals(text)) {
                    document.close();
                    break;
                }

                document.edit(text);
                if (times == 5) {
                    document.save(false);
                    times = 0;
                }
                times++;

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}