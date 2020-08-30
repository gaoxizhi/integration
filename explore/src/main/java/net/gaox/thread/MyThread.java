package net.gaox.thread;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2020/5/10 12:16
 */
public class MyThread implements Runnable {
    private static Integer taskSize = 10;

    @Override
    public void run() {
        System.out.println("MyThread.run()");
    }

    public static void main(String[] args) {

        //启动 MyThread，需要首先实例化一个 Thread，并传入自己的 MyThread 实例:
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        thread.start();

        newScheduledThreadPool();

        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < taskSize + 15; i++) {
            Callable c = new MyCallable(String.valueOf(i + 1));
            // 执行任务并获取 Future 对象
            Future f = pool.submit(c);
            list.add(f);
        }
        // 关闭线程池
        pool.shutdown();
        try {
            // 获取所有并发任务的运行结果
            for (Future f : list) {
                // 从 Future 对象上获取任务的返回值，并输出到控制台
                System.out.println("res:" + f.get().toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static void newScheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟三秒后执行一次");
            }
        }, 3, TimeUnit.SECONDS);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟 1 秒后每三秒执行一次");
            }
        }, 1, 3, TimeUnit.SECONDS);
    }
}

@Data
@NoArgsConstructor
class MyCallable implements Callable {

    private String index;

    MyCallable(String index) {
        this.index = index;
    }

    @Override
    public Object call() throws Exception {
        Thread.sleep(2_000);
        return String.format("任务[%s]已完成！", index);
    }
}