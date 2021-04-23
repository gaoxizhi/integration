package net.gaox.javaSE2018.thread.rearrangement;

/**
 * @Description: <p>  </p>
 * @ClassName SellTicket
 * @Author: gaox·Eric
 * @Date: 2019/3/31 18:53
 */
public class SellTicket extends Thread {
    private String name;
    private int num = 50;

    public SellTicket(String name) {
        super();
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= num; i++) {
            System.out.println(name + "卖出了第" + i + "张票！");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
