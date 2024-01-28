package net.gaox.thread.lock.readWrite;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> 读写锁测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 18:06
 */
@Slf4j
public class ReadWriteLockTest {

    /**
     * 数据源，字符串模版
     */
    private final static String text = "Thisistheexampleforreadwritelock1";

    public static void main(String[] args) throws InterruptedException {
        final ShareData shareData = new ShareData(50);

        // 启动两个写线程，争夺写锁
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    for (int index = 0; index < text.length(); index++) {
                        try {
                            char c = text.charAt(index);
                            log.info("write " + c);
                            shareData.write(c);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "write-thread-" + i).start();
        }

        // 10个读线程，共享读锁
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        char[] read = shareData.read();
                        log.info("read " + new String(read));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "read-thread-" + i).start();
        }
    }

}
