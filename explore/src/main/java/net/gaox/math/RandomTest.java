package net.gaox.math;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <p> Random 新特性测试 </p>
 *
 * @author gaox·Eric
 * @date 2020/4/2 00:13
 */
public class RandomTest {
    @Test
    public void test() {
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
