package net.gaox.thread.flight;

/**
 * <p> 安检案例，多线程数据同步异常 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 14:50
 */
public class FlightSecurityTest {

    public static void main(String[] args) {
        final FlightSecurity flightSecurity = new FlightSecurity();
        new Passengers(flightSecurity, "A123456", "AF123456").start();
        new Passengers(flightSecurity, "B123456", "BF123456").start();
        new Passengers(flightSecurity, "C123456", "CF123456").start();
    }

}
