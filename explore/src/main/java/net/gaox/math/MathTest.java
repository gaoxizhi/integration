package net.gaox.math;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> Math 数学工具包 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-18 20:05
 */
@Slf4j
public class MathTest {

    public static void main(String[] args) {
        double cbrtValue = 27.0;
        double cbrt = Math.cbrt(cbrtValue);
        log.info("{} 的 立方根 cbrt = {}", cbrtValue, cbrt);

        double sqrtValue1 = 4.000000000000001;
        double sqrt = Math.sqrt(sqrtValue1);
        log.info("{} 的 平方根 cbrt = {}", sqrtValue1, sqrt);
        double sqrtValue2 = 4.000000000000002;
        double sqrt2 = Math.sqrt(sqrtValue2);
        log.info("{} 的 平方根 cbrt = {}", sqrtValue2, sqrt2);
    }
}
