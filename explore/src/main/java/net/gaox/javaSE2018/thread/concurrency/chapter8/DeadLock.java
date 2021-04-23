package net.gaox.javaSE2018.thread.concurrency.chapter8;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */
public class DeadLock {

    private OtherService otherService;

    public DeadLock(OtherService otherService) {
        this.otherService = otherService;
    }

    private final Object lock = new Object();

    public void m1() {
        synchronized (lock) {
            System.out.println("m1");
            otherService.s1();
        }
    }

    public void m2() {
        synchronized (lock) {
            System.out.println("m2");
        }
    }
}
