package net.gaox.thread.flight;

/**
 * <p> 乘客类 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 14:51
 */
public class Passengers extends Thread {

    private final FlightSecurity flightSecurity;

    private final String idCard;

    private final String boardingPass;

    public Passengers(FlightSecurity flightSecurity, String idCard, String boardingPass) {
        this.flightSecurity = flightSecurity;
        this.idCard = idCard;
        this.boardingPass = boardingPass;
    }

    @Override
    public void run() {
        while (true) {
            flightSecurity.pass(boardingPass, idCard);
        }
    }

}
