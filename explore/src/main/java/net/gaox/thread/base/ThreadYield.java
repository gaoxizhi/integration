package net.gaox.thread.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <p> 让出CPU给其他线程执行: 你人还怪好嘞 </p>
 * yield()方法并不会释放锁，线程依然处于RUNNABLE状态，线程不会进入堵塞状态
 * 不能保证在调用后一定能达到预期的效果
 *
 * @author gaox·Eric
 * @date 2021/4/27 22:28
 */
@Slf4j
public class ThreadYield {
    public static void main(String[] args) throws InterruptedException {

        IntStream.rangeClosed(1, 5).mapToObj(ThreadYield::create).forEach(Thread::start);
        TimeUnit.SECONDS.sleep(1);
        log.info("==================next==================");

        IntStream.rangeClosed(1, 5).mapToObj(index -> new Thread(() -> monitor(index)))
                .forEach(Thread::start);
    }

    private static Thread create(int index) {
        return new Thread(() -> {
            log.info("index = {}", index);
            // 调用yield方法
            if (1 == index % 3) {
                Thread.yield();
            }
        });
    }

    private static void monitor(int index) {
        log.info("index {} enter.", index);
        // 调用yield方法
        if (1 == index % 2) {
            Thread.yield();
        }
        log.info("index {} do task.", index);
        log.info("index {} leave.", index);
    }

}
