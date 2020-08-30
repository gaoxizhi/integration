package net.gaox.clone;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/11/30 15:23
 */
public class App {
    public static void main(String[] args) throws CloneNotSupportedException {
        Audi a4 = new Audi("A4", LocalDateTime.of(2019, 10, 24, 0, 0), new BigDecimal("350000"));
        HongQi h7 = new HongQi("H7", LocalDateTime.of(2019, 10, 24, 0, 0), new BigDecimal("31000"));
        Audi newA4 = a4.clone();

        HongQi newH7 = h7.clone();
        System.out.println(newA4);
        System.out.println(newH7);
    }
}
