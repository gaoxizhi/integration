package net.gaox.util.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <p> 新特性测试 </p>
 *
 * @author gaox·Eric
 * @date 2020/4/2 00:13
 */
public class Java8Test {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("12");
        BigDecimal b = new BigDecimal("6.2");
        // ArithmeticException:
        // 无穷小数扩张;没有精确的可表示的小数结果。
        // Non-terminating decimal expansion; no exact representable decimal result.
        // System.out.println(a.divide(b));
        System.out.println(a.divide(new BigDecimal("2")));
        System.out.println(a.divide(b, 2, RoundingMode.HALF_UP));

        System.out.println(new BigDecimal("12").multiply(new BigDecimal("2.3")));

        try {
            SecureRandom random1 = SecureRandom.getInstance("SHA1PRNG");
            SecureRandom random2 = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < 5; i++) {
                int i1 = random1.nextInt();
                int i2 = random2.nextInt();
                System.out.println(i1 == i2 ? i1 + " = " + i2 : i1 + " != " + i2);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
