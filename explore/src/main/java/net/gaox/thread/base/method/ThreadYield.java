package net.gaox.thread.base.method;

import java.util.stream.IntStream;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2021/4/27 22:28
 */
public class ThreadYield {
    public static void main(String[] args) {
        IntStream.rangeClosed(1, 2).mapToObj(ThreadYield::create).forEach(Thread::start);
    }

    private static Thread create(int index) {
        return new Thread(() -> {
            // 调用yield方法
            if (1 == index) {
                Thread.yield();
            }
            System.out.println(index);
        });
    }
}
