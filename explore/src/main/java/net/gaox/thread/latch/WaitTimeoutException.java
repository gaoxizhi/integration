package net.gaox.thread.latch;

/**
 * <p> 子任务线程执行超时异常 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 13:32
 */
public class WaitTimeoutException extends Exception {

    public WaitTimeoutException(String message) {
        super(message);
    }

}
