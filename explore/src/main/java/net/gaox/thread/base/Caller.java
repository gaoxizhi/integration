package net.gaox.thread.base;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * <p> Callable </p>
 *
 * @author gaox·Eric
 * @date 2020/3/9 12:37
 */
public class Caller implements Callable<Boolean> {
    @Override
    public Boolean call() {
        try {
            Thread.sleep(10_000);
            System.out.println(new Date());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}