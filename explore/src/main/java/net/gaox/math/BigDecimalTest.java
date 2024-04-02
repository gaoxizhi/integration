package net.gaox.math;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p> BigDecimal 新特性测试 </p>
 *
 * @author gaox·Eric
 * @date 2020/4/2 00:13
 */
public class BigDecimalTest {
    @Test
    public void test() {
        BigDecimal a = new BigDecimal("12");
        BigDecimal b = new BigDecimal("6.2");
        // ArithmeticException:
        // 无穷小数扩张;没有精确的可表示的小数结果。
        // Non-terminating decimal expansion; no exact representable decimal result.
        // System.out.println(a.divide(b));

        System.out.println(a.divide(new BigDecimal("2")));
        System.out.println(a.divide(b, 2, RoundingMode.HALF_UP));

        System.out.println(new BigDecimal("12").multiply(new BigDecimal("2.3")));
    }

}
