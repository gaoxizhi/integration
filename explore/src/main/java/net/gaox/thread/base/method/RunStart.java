package net.gaox.thread.base.method;

import java.util.stream.IntStream;

/**
 * <p> run 和 start 方法的区别 </p>
 * 当程序调用start()方法时，会创建一个新线程，然后执行run()方法。
 * 但是如果我们直接调用run()方法，则不会创建新的线程，
 * run()方法将作为当前调用线程本身的常规方法调用执行，并且不会发生多线程。
 *
 * @author gaox·Eric
 * @date 2021/4/25 08:24
 */
public class RunStart {
    private final static String PREFIX = "GAO_THREAD-";

    public static void main(String[] args) {
        IntStream.range(0, 5).mapToObj(RunStart::createThread).forEach(Thread::run);
        IntStream.range(0, 5).boxed()
                .map(i -> new Thread(() -> System.out.println(Thread.currentThread().getName()), PREFIX + i))
                .forEach(Thread::start);
        // 区别以上，输出当前线程名为${PREFIX}
        new Thread(() -> System.out.println(Thread.currentThread().getName()), PREFIX + "one").start();
    }

    //
    private static Thread createThread(final int intName) {
        return new Thread(() -> System.out.println(Thread.currentThread().getName()), PREFIX + intName);
    }
}
