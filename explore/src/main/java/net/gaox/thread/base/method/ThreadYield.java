package net.gaox.thread.base.method;

import lombok.extern.slf4j.Slf4j;

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
    public static void main(String[] args) {
        IntStream.rangeClosed(1, 5).mapToObj(ThreadYield::create).forEach(Thread::start);
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

}
