package net.gaox.thread.strange;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p> hashmap 多线程操作，出现死循环引起的死锁 </p>
 * 在多线程同时写操作的情况下不对其进行同步化封装，则很容易出现死循环引起的死锁
 *
 * @author gaox·Eric
 * @date 2024-01-23 10:27
 */
public class HashMapDeadLock {
    private final HashMap<String, String> map = new HashMap<>();

    public void add(String key, String value) {
        this.map.put(key, value);
    }

    public static void main(String[] args) {

        final HashMapDeadLock map = new HashMapDeadLock();
        for (int x = 0; x < 2; x++) {
            new Thread(() -> {
                for (int i = 1; i < Integer.MAX_VALUE; i++) {
                    map.add(String.valueOf(i), String.valueOf(i));
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
