package net.gaox.thread.timer;

/**
 * <p> 超时主动关闭 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/9 12:21
 */
public class ProcessThread implements Runnable {
    private LittleThread mt;
    private Thread thread;
    private long processTime;

    public ProcessThread(LittleThread mt, long processTime) {
        super();
        this.mt = mt;
        this.processTime = processTime;
    }

    @Override
    public void run() {
        System.out.println("ThreadID:" + thread.getId() + " >>> starting!");
        doSomething();
        mt.notifyLock();
        System.out.println("ThreadID:" + thread.getId() + " >>> ending ok!");

    }

    private void doSomething() {
        try {
            // 异常情况
            // Thread.sleep(100 * 1000);
            // 正常情况
            // Thread.sleep(1 * 100);

            Thread.sleep(processTime);

            System.out.println("ThreadID:" + thread.getId() + " >>> Normal Process! processTime = " + processTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("ThreadID: " + thread.getId() + " >>> AbNormal Process! processTime = " + processTime);
        }

    }

    public void start() {
        thread = new Thread(this);
        thread.start();

    }

    public void stop() {
        // 如果任务在正常时间内不能退出，认为产生interrupt,强行地退出 （run方法正常结束）
        thread.interrupt();
        thread.stop();
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ThreadID:" + thread.getId() + " >>> stopping end!");
        thread = null;
    }

}