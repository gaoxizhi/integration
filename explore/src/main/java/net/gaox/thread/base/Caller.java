package net.gaox.thread.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * <p> Callable </p>
 *
 * @author gaox·Eric
 * @date 2020/3/9 12:37
 */
@Slf4j
public class Caller implements Callable<Boolean> {
    @Override
    public Boolean call() {
        try {
            Thread.sleep(2_000);
            log.info("运行完成……");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}