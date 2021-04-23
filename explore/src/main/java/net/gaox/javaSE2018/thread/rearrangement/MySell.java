package net.gaox.javaSE2018.thread.rearrangement;
/**
 * @Description: <p>  </p>
 * @ClassName MySell
 * @Author: gaox·Eric
 * @Date: 2019/3/31 18:54
 */
public class MySell implements Runnable {
    private int num = 50;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        for (int i = 1; i <= num; i++) {
            System.out.println(Thread.currentThread().getName() + "卖出了第" + i
                    + "张票！");
        }
    }
}


