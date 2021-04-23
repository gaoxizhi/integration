package net.gaox.javaSE2018.thread.concurrency.chapter1;

import java.time.LocalDateTime;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */
public class TryConcurrency {

    public static void main(String[] args) {
        Thread t = new Thread("READ-Thread") {
            @Override
            public void run() {
                readFromDataBase();
            }
        };

        t.start();

        new Thread("WRITE-Thread") {
            @Override
            public void run() {
                writeDataToFile();
            }
        }.start();
    }

    private static void readFromDataBase() {
        //read data from database and handle it.
        try {
            String threadName = Thread.currentThread().getName();
            println(threadName, "Begin read data from db.");
            Thread.sleep(1000 * 3L);
            println(threadName, "Read data done and start handle it.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String threadName = Thread.currentThread().getName();
        println(threadName, "The data handle finish and successfully.");
    }

    private static void writeDataToFile() {
        try {
            String threadName = Thread.currentThread().getName();
            println(threadName, "Begin write data to file.");
            Thread.sleep(2000 * 2L);
            println(threadName, "Write data done and start handle it.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String threadName = Thread.currentThread().getName();
        println(threadName, "The data handle finish and successfully.");
    }

    private static void println(String threadName, String message) {
        String format = String.format("%s [%s] : %s", LocalDateTime.now(), threadName, message);
        System.out.println(format);
    }
}
