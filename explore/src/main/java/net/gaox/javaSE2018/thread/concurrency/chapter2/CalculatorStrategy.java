package net.gaox.javaSE2018.thread.concurrency.chapter2;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */

@FunctionalInterface
public interface CalculatorStrategy {

    double calculate(double salary, double bonus);
}
