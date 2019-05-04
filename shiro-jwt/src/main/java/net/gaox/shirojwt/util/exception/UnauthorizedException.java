package net.gaox.shirojwt.util.exception;

/**
 * @Description: <p> 用户未登录异常 </p>
 * @ClassName UnauthorizedException
 * @Author: gaox·Eric
 * @Date: 2019/5/4 00:46
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}