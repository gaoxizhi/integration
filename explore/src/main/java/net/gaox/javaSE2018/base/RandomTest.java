package net.gaox.javaSE2018.base;

import java.util.Random;

/**
 * @Description: <p> 随机数案例 </p>
 * @ClassName RandomTest
 * @Author: gaox·Eric
 * @Date: 2019/3/31 16:26
 */
public class RandomTest {

    public static void main(String[] args) {

        final int times = 30;
        System.out.println("nihao");
        for (int i = 0; i < times; i++) {
            System.out.print((int) (Math.random() * 10) + " ");
        }
        System.out.println();
        for (int i = 0; i < times; i++) {
            System.out.print(new Random().nextInt(10) + " ");
        }
        System.out.println();

        // 案例3
        // 在没带参数构造函数生成的Random对象的种子缺省是当前系统时间的毫秒数。
        Random r3 = new Random();
        System.out.println("使用种子缺省是当前系统时间的毫秒数的Random对象生成[0,10)内随机整数序列");
        for (int i = 0; i < times; i++) {
            System.out.print(r3.nextInt(10) + " ");
        }
        /**
         * 输出结果为：
         *
         * 使用种子缺省是当前系统时间的毫秒数的Random对象生成[0,10)内随机整数序列
         * 1 1 0 4 4 2 3 8 8 4
         *
         */

    }

}
