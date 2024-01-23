package net.gaox.thread.gaoxpool.exception;

/**
 * <p> 拒绝策略异常 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:17
 */
public class RunnableDenyException extends RuntimeException {

    public RunnableDenyException(String message) {
        super(message);
    }
}