package net.gaox.base;

import java.util.Random;

public class randoms {

	public static void main(String[] args) {
		System.out.println("nihao");
		System.out.println((int)(Math.random() * 100 ));
		System.out.println(new Random().nextInt(10));

        
        // 案例3
        // 在没带参数构造函数生成的Random对象的种子缺省是当前系统时间的毫秒数。
        Random r3 = new Random();
        System.out.println();
        System.out.println("使用种子缺省是当前系统时间的毫秒数的Random对象生成[0,10)内随机整数序列");
        for (int i = 0; i < 30; i++) {
            System.out.print(r3.nextInt(10)+" ");
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
