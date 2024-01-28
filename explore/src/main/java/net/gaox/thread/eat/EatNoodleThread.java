package net.gaox.thread.eat;

/**
 * <p> 吃面 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 14:16
 */
public class EatNoodleThread extends Thread {
    private final String name;

    /**
     * 将 liftTool 与 rightTool 封装到 TablewarePair 对象中
     * 同一时间只能有一个线程获得刀和叉，解决多线程争夺两个资源产生死锁问题
     */
    private final TablewarePair tablewarePair;

    public EatNoodleThread(String name, TablewarePair tablewarePair) {
        this.name = name;
        this.tablewarePair = tablewarePair;
    }

    @Override
    public void run() {
        while (true) {
            this.eat();
        }
    }

    private void eat() {
        synchronized (tablewarePair) {
            System.out.println(name + " take up " + tablewarePair.getLeftTool() + "(left)");
            System.out.println(name + " take up " + tablewarePair.getRightTool() + "(right)");
            System.out.println(name + " is eating now.");
            System.out.println(name + " put down " + tablewarePair.getRightTool() + "(right)");
            System.out.println(name + " put down " + tablewarePair.getLeftTool() + "(left)");
        }
    }

}
