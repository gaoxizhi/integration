package net.gaox.thread.alternating;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> 三个线程A、B、C 交替执行 要求输出的结果必须按顺序执行 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-21 17:22
 */
@Slf4j
public class ABCAlternate {
    private static final int time = 20;

    public static void main(String[] args) {
        AlternateCondition condition = new AlternateCondition();

        new Thread(() -> {
            for (int i = 1; i <= time; i++) {
                condition.loopA(i);
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= time; i++) {
                condition.loopB(i);
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= time; i++) {
                condition.loopC(i);
                log.info("-----------------------------------");
            }
        }, "C").start();
    }

}
