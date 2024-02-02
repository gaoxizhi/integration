package net.gaox.message.driven.exception;

/**
 * <p> Message 无法匹配异常 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 08:35
 */
public class MessageMatcherException extends RuntimeException {
    public MessageMatcherException(String message) {
        super(message);
    }
}
