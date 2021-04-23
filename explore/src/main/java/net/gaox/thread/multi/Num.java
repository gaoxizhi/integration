package net.gaox.thread.multi;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2021/4/24 00:00
 */
public class Num {
    static int intNum = 0;
    static volatile int intVolatileNum = 0;
    static AtomicInteger atomicNum = new AtomicInteger(0);
    static volatile AtomicInteger atomicVolatileNum = new AtomicInteger(0);

    /**
     * static int 自增和获取
     *
     * @return
     */
    public static int getIntNum() {
        return intNum;
    }

    public static void increaseIntNum() {
        intNum++;
    }


    public static int getIntVolatileNum() {
        return intVolatileNum;
    }

    public static void increaseIntVolatileNum() {
        intVolatileNum++;
    }

    public static AtomicInteger getAtomicNum() {
        return atomicNum;
    }

    public static void increaseAtomicNum() {
        atomicNum.getAndIncrement();
    }

    public static AtomicInteger getAtomicVolatileNum() {
        return atomicVolatileNum;
    }

    public static void increaseAtomicVolatileNum() {
        atomicVolatileNum.getAndIncrement();
    }
}
