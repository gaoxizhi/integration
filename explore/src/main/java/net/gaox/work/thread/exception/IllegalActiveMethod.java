package net.gaox.work.thread.exception;

/**
 * <p> 异步消息执行异常 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 13:53
 */
public class IllegalActiveMethod extends Exception {
    public IllegalActiveMethod(String message) {
        super(message);
    }

}
