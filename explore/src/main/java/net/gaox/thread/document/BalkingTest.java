package net.gaox.thread.document;

/**
 * <p> 犹豫模式 测试 </p>
 * word 自动保存场景
 *
 * @author gaox·Eric
 * @date 2024-01-29 11:50
 */
public class BalkingTest {
    public static void main(String[] args) {
        new DocumentEditThread("file", "balking.txt").start();
    }
}

