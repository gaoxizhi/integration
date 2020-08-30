package net.gaox.thread.pool;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * <p> 线程池测试 </p>
 *
 * @author gaox·Eric
 * @date 2020/5/7 16:12
 */
public class Test {

    private static int count = 20_000;

//    LinkedList
    static void test() {
        long startTime = System.currentTimeMillis();

        ConcurrentLinkedDeque<Integer> l = new ConcurrentLinkedDeque<>();
        // 不要提交大量超过线程池处理能力的任务，这时可能会导致队列饱和，抛出异常
        ThreadPoolExecutor tp = new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(count));
        final Random random = new Random();

        for (int i = 0; i < count; i++) {
            tp.execute(() -> l.add(random.nextInt()));
        }

        // 当调用了线程池的shutdown()方法以后，不要提交新任务给线程池
        tp.shutdown();
        try {
            tp.awaitTermination(1, TimeUnit.DAYS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 直到线程池任务完成，跳出
        while (true) {
            if (tp.isTerminated()) {
                break;
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(l.size());
    }

    static void test2() {
        long startTime = System.currentTimeMillis();
        final List<Integer> l = new LinkedList<>();
        final Random random = new Random();

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> l.add(random.nextInt()));
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(l.size());
    }

    static void test3() {
        ExecutorService executor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(15));

        Worker tasks[] = new Worker[20];
        for (int i = 0; i < 20; i++) {
            tasks[i] = new Worker(i);
            System.out.println("提交任务: " + tasks[i] + ", " + i);
            executor.execute(tasks[i]);
        }
        System.out.println("主线程结束");
        executor.shutdown();
        // 关闭线程池
    }

    static void test4() {
        ExecutorService executor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(15));
        Worker tasks[] = new Worker[20];
        for (int i = 0; i < 10; i++) {
            tasks[i] = new Worker(i);
            System.out.println("提交任务: " + tasks[i] + ", " + i);
            executor.execute(tasks[i]);
        }
        // 让主线程睡眠三秒，足以处理完成以上任务
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 10; i < 20; i++) {
            tasks[i] = new Worker(i);
            System.out.println("提交任务: " + tasks[i] + ", " + i);
            executor.execute(tasks[i]);
        }
        System.out.println("主线程结束");
        // 关闭线程池
        executor.shutdown();
    }

    static void test5() {
        final List<Integer> l = new LinkedList<>();
        final Semaphore semaphore = new Semaphore(10);
        ExecutorService exec = Executors.newCachedThreadPool();
        final Random random = new Random();

        final long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Runnable task = () -> {
                try {
                    semaphore.acquire();
                    l.add(random.nextInt());
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            exec.submit(task);
        }
        exec.shutdown();
        try {
            exec.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("---END--- count: %7d\n", l.size());
        final long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    static class Worker implements Runnable {
        private int id;

        public Worker(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 执行任务 " + id);
//                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " 完成任务 " + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        test();
//        test2();
//        test3();
//        test4();
        test();
    }
}
