package net.gaox.javaSE2018.thread.concurrency.chapter7;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */
public class SychronizedStaticTest {
    public static void main(String[] args) {
        new Thread("T1") {
            @Override
            public void run() {
                SychronizedStatic.m1();
            }
        }.start();

        new Thread("T2") {
            @Override
            public void run() {
                SychronizedStatic.m2();
            }
        }.start();

        new Thread("T3") {
            @Override
            public void run() {
                SychronizedStatic.m3();
            }
        }.start();
    }
}
