package net.gaox.util.exception;

/**
 * <p> 用户未登录异常 </p>
 *
 * @author gaox·Eric
 * @date 2019/5/4 00:46
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}