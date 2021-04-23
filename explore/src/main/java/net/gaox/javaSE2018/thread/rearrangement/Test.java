package net.gaox.javaSE2018.thread.rearrangement;

/**
 * @Description: <p> 线程的执行顺序 </p>
 * @ClassName Test
 * 可能并不是按照我们的意愿进行执行的，JVM可能会重排序
 * @Author: gaox·Eric
 * @Date: 2019/3/31 18:55
 */
public class Test {
    public static void main(String[] args) throws Exception {
        new SellTicket("A").start();
        new SellTicket("B").start();
        new SellTicket("C").start();

        new Thread(new MySell(), "D").start();
        new Thread(new MySell(), "E").start();
        new Thread(new MySell(), "F").start();

        for (int i = 10; i > 0; i--) {
            System.out.println(i);
            Thread.sleep(10);
        }
    }
}
