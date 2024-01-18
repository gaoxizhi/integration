package net.gaox.thread.base.method;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
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
@Slf4j
public class RunStart {
    private final static String PREFIX = "GAO_THREAD-";
    private static Runnable runnable = () -> log.info(Thread.currentThread().getName() + " -  in runnable.");

    public static void main(String[] args) throws InterruptedException {
        // run()方法是线程的一个普通方法，它描述了线程执行的操作
        // 当你调用run()方法时，实际上是在当前线程中执行了这个方法，而不是创建了一个新的线程
        IntStream.range(0, 5).mapToObj(RunStart::createThread).forEach(Thread::run);
        // start()方法用于启动一个新的线程
        // 当调用start()方法时，当前线程会变为就绪状态，等待CPU分配时间片，一旦CPU有可用时间，就会执行run()方法
        IntStream.range(0, 5).boxed().map(i -> new Thread(runnable, PREFIX + i)).forEach(Thread::start);

        Thread thread = new Thread(runnable, PREFIX + "self");
        // run方法可以多次执行
        thread.run();
        thread.run();

        Thread runnableJoinThread = new Thread(runnableJoinThread(), PREFIX + "runnableJoinThread");
        runnableJoinThread.start();
        // start()方法只能被调用一次，如果重复调用，会抛出 java.lang.IllegalThreadStateException 异常
        // runnableJoinThread.start();

        // join某个线程A，会使当前线程B进入等待，直到线程A结束生命周期或者到达给定的时间，那么在此期间B线程是处于 BLOCKED 的
        runnableJoinThread.join();

        // 如果上面没有加上join方法，本行会立即输出
        log.info("立即输出？");
    }

    private static Thread createThread(final int intName) {
        return new Thread(() -> log.info(Thread.currentThread().getName() + " - in createThread."),
                PREFIX + intName);
    }

    private static Thread runnableJoinThread() {
        return new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName() + " - in runnableJoinThread.");
        });
    }

}
