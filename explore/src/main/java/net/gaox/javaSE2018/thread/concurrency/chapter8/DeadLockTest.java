package net.gaox.javaSE2018.thread.concurrency.chapter8;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */
public class DeadLockTest {
    public static void main(String[] args) {
        OtherService otherService = new OtherService();
        DeadLock deadLock = new DeadLock(otherService);
        otherService.setDeadLock(deadLock);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    deadLock.m1();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true)
                    otherService.s2();
            }
        }.start();
    }
}