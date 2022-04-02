package net.gaox.thread;

/**
 * <p> 买票窗口服务 </p>
 *
 * @author gaox·Eric
 * @Date: 2019/3/31 18:54
 */
public class SellServer implements Runnable {
    private int num = 50;

    @Override
    public void run() {
        for (int i = 1; i <= num; i++) {
            System.out.println(Thread.currentThread().getName() + "卖出了第 " + i + " 张票！");
        }
    }

}
