package net.gaox;

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
            if (x == 0 && y == 0) {
                System.out.println("第" + i + "次(" + x + "," + y + ")");
                break;
            }
        }
        System.out.println(LocalDateTime.now());
    }
}
