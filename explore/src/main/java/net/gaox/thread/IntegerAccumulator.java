package net.gaox.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <p> 不可变对象，线程安全 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 22:36
 */
@Slf4j
public final class IntegerAccumulator {

    /**
     * 初始值
     */
    private final int init;

    /**
     * 构造时传入初始值
     *
     * @param init 初始值
     */
    public IntegerAccumulator(int init) {
        this.init = init;
    }

    /**
     * 构造新的累加器，需要用到另外一个 accumulator 和 初始值
     *
     * @param accumulator 另外一个 accumulator
     * @param init        初始值
     */
    public IntegerAccumulator(IntegerAccumulator accumulator, int init) {
        this.init = accumulator.getValue() + init;
    }

    /**
     * 每次相加都会产生一个新的 IntegerAccumulator
     *
     * @param i 加数
     * @return 新对象
     */
    public IntegerAccumulator add(int i) {
        return new IntegerAccumulator(this, i);
    }

    /**
     * 获取当前值
     *
     * @return 当前值
     */
    public int getValue() {
        return this.init;
    }

    public static void main(String[] args) {
        IntegerAccumulator accumulator = new IntegerAccumulator(0);
        IntStream.range(0, 3).forEach(i -> new Thread(() -> {
            int inc = 0;
            while (true) {
                int oldValue = accumulator.getValue();
                int result = accumulator.add(inc).getValue();
                log.info("oldValue: {}, inc = {}, result = {}", oldValue, inc, result);
                if (inc + oldValue != result) {
                    log.error("ERROR: : {}, inc = {}, result = {}", oldValue, inc, result);
                }
                inc++;
                slowly();
            }
        }).start());
    }

    private static void slowly() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

