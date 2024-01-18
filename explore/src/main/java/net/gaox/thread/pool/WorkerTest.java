package net.gaox.thread.pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;

/**
 * <p> 线程池测试 </p>
 *
 * @author gaox·Eric
 * @date 2020/5/7 16:12
 */
@Slf4j
public class WorkerTest {

    private static int count = 20_000;

    /**
     * 直接使用等同数量的线程实现
     */
    @Test
    public void sameQuantityThreads() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        final List<Integer> l = new LinkedList<>();
        final Random random = new Random();

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> l.add(random.nextInt()));
            thread.start();

            thread.join();
        }
        log.info("耗时: {}", System.currentTimeMillis() - startTime);
        log.info("执行次数: {}, sum = {}", l.size(),
                l.stream().filter(Objects::nonNull).reduce(0, Integer::sum).longValue());
    }

    /**
     * 使用线程池控制
     */
    @Test
    public void ThreadPoolExecutorTest() {
        long startTime = System.currentTimeMillis();

        // ConcurrentLinkedDeque（简称CLD）是Java中的一个线程安全的双端队列实现
        // ConcurrentLinkedQueue 是Java中的一个线程安全的非阻塞队列实现，其内部是一个单向链表
        // CopyOnWriteArrayList 是Java中提供的一种线程安全的List集合
        // ========================================================================
        // CopyOnWriteArrayList 和 ConcurrentLinkedQueue 使用了"写时复制"的策略
        // 当需要对队列进行修改时，它会创建一个新的副本，在副本中修改，再赋值给原对象
        // 在理论上提供了线程安全，它的读操作是线程安全的
        // 但在读的过程中，如果数据被其他线程修改，是无法实时感知到最新的数据变化的
        // 它的写入操作会有一定的内存压力，因为在写操作中需要复制原数组
        // 而不是直接在原有的数据结构上进行修改，这样可以确保读取操作不受修改操作的影响

        ConcurrentLinkedDeque<Integer> l = new ConcurrentLinkedDeque<>();
        // 不要提交大量超过线程池处理能力的任务，这时可能会导致队列饱和，抛出异常
        ThreadPoolExecutor tp = new ThreadPoolExecutor(4, 8,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(count));
        final Random random = new Random();

        for (int i = 0; i < count; i++) {
            tp.execute(() -> l.add(random.nextInt()));
        }

        // 当调用了线程池的shutdown()方法以后，不要提交新任务给线程池
        tp.shutdown();

        // 直到线程池任务完成，跳出
        while (true) {
            if (tp.isTerminated()) {
                break;
            }
        }
        log.info("耗时: {}", System.currentTimeMillis() - startTime);
        log.info("执行次数: {}, sum = {}", l.size(),
                l.stream().filter(Objects::nonNull).reduce(0, Integer::sum).longValue());
    }

    /**
     * 使用封装实现 Runnable 的工作类
     */
    @Test
    public void ExecutorServiceImplRunnableTest() throws InterruptedException {
        // 需要注意 maximumPoolSize 最大线程线程数 和 queue 长度
        ExecutorService executor = new ThreadPoolExecutor(4, 8,
                0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(count));

        for (int i = 0; i < count; i++) {
            Worker worker = new Worker(i + 1);
            log.info("提交任务: {}", i + 1);
            executor.execute(worker);
        }

        // 关闭线程池，需要等待所有线程都执行完成
        executor.shutdown();
        // ExecutorService 关闭后才能调用
        // 用于等待所有任务执行完毕，这样可以避免线程池中的任务未执行完毕导致的问题
        // 方法会阻塞当前线程，直到所有任务完成(返回true)或者达到指定的时间(返回false)
        while (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
        }

        log.info("主线程结束");
    }

    /**
     * 使用封装实现 Runnable 的工作类，并且使用到 maximumPoolSize
     */
    @Test
    public void ExecutorServiceImplRunnableUseMaximumPoolSizeTest() throws InterruptedException {
        ExecutorService executor = new ThreadPoolExecutor(4, 8,
                0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(20 - 8));

        for (int i = 0; i < 20; i++) {
            Worker worker = new Worker(i + 1);
            log.info("提交任务: {}", i + 1);
            executor.execute(worker);
        }

        executor.shutdown();
        while (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
        }

        log.info("主线程结束");
    }

    /**
     * Semaphore 信号量，同步排队
     * 银行取号窗口排队
     */
    @Test
    public void SemaphoreTest() throws InterruptedException {
        final long start = System.currentTimeMillis();
        int workCount = 100;
        // 虽然有4个核心线程，但是只能同时执行2个Work
        final Semaphore semaphore = new Semaphore(2);
        ExecutorService executor = new ThreadPoolExecutor(4, 8,
                0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(workCount));

        for (int i = 0; i < workCount; i++) {
            executor.submit(new WorkerWithSemaphore(i + 1, semaphore));
        }
        executor.shutdown();

        while (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
        }

        log.info("耗时: {}", System.currentTimeMillis() - start);
    }

}
