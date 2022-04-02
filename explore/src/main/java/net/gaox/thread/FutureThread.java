package net.gaox.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p> 异步线程任务 </p>
 *
 * @author gaox·Eric
 * @date 2020/5/10 12:16
 */
public class FutureThread {
    private static Integer taskSize = 10;

    public static void main(String[] args) {

        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < taskSize + 15; i++) {
            Callable c = new CallableTask(String.valueOf(i + 1));
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
                System.out.println("res: " + f.get().toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
