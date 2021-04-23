package net.gaox.thread;

import java.time.LocalDateTime;

/**
 * <p> 指令重排 </p>
 *
 * @author gaox·Eric
 * @date 2020/5/6 16:28
 */
public class PossibleReordering {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        final long start = System.currentTimeMillis();
        System.out.println(LocalDateTime.now());
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread one = new Thread(() -> {
                a = 1;
                x = b;
            });
            Thread two = new Thread(() -> {
                b = 1;
                y = a;
            });
            one.start();
            two.start();
            one.join();
            two.join();
            // 理论上讲 该次循环执行顺序可能为以下情况
            /*
             one：x = b;
             two：y = a;
             其他两条
             ------------
             one：x = b;
             two：b = 1;
             two：y = a;
             one：a = 1;
             */
            if (x == 0 && y == 0) {
                System.out.println("第" + i + "次(" + x + "," + y + ")");
                break;
            }
        }
        System.out.println(LocalDateTime.now());
        long time = (System.currentTimeMillis() - start) / 1000;
        System.out.println(String.format("本次测试总耗时：%4d秒。", time));
    }
}
