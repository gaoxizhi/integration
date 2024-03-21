package net.gaox.thread.lock.readWrite;

import net.gaox.thread.lock.readWrite.lock.ReadWriteLocks;

/**
 * <p> 读写锁测试2 </p>
 * 测试结果：
 * 读和前一个写，相差5ms
 * 读和读互相不影响，多个读可以相同时间，或者小于2ms
 *
 * @author gaox·Eric
 * @date 2024-03-21 18:05
 */
public class ReadWriteLockTest2 {

    public static void main(String[] args) {
        ReadWriteLocks rwLook = new ReadWriteLocks();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                new Thread(() -> rwLook.set((int) (Math.random() * 101)), String.format("Write-%03d", i)).start();
            }
        }, "WriteCreat").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                new Thread(rwLook::get, String.format("Read-%03d", i)).start();
            }
        }, "ReadCreat").start();

    }

}
