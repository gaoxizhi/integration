package net.gaox.thread.pool;

import java.util.concurrent.TimeUnit;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2021/4/27 20:53
 */
class Worker implements Runnable {
    private int id;

    public Worker(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " 执行任务 " + id);
            TimeUnit.MILLISECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + " 完成任务 " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
