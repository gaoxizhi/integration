package net.gaox.javaSE2018.thread.concurrency.chapter2;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */
public class Bank {
    public static void main(String[] args) {



        TicketWindow ticketWindow1 = new TicketWindow("一号柜台");
        ticketWindow1.start();

        TicketWindow ticketWindow2 = new TicketWindow("二号柜台");
        ticketWindow2.start();

        TicketWindow ticketWindow3 = new TicketWindow("三号柜台");
        ticketWindow3.start();

    }
}
