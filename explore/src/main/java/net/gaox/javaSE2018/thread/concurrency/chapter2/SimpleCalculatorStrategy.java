package net.gaox.javaSE2018.thread.concurrency.chapter2;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */
public class SimpleCalculatorStrategy implements CalculatorStrategy {

    private final static double SALARY_RATE = 0.1;
    private final static double BONUS_RATE = 0.15;

    @Override
    public double calculate(double salary, double bonus) {
        return salary * SALARY_RATE + bonus * BONUS_RATE;
    }
}
