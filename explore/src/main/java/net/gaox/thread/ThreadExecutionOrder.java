package net.gaox.thread;

/**
 * <p> 线程的执行顺序 </p>
 * 可能并不是按照我们的意愿进行执行的，JVM可能会重排序
 *
 * @author gaox·Eric
 * @Date: 2019/3/31 18:55
 */
public class ThreadExecutionOrder {

    public static void main(String[] args) throws Exception {
        new SellTicket("A").start();
        new SellTicket("B").start();
        new SellTicket("C").start();

        new Thread(new SellServer(), "D").start();
        new Thread(new SellServer(), "E").start();
        new Thread(new SellServer(), "F").start();

        for (int i = 10; i > 0; i--) {
            System.out.println(i);
            Thread.sleep(10);
        }
    }

}
