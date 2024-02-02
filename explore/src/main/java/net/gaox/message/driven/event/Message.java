package net.gaox.message.driven.event;

/**
 * <p> Message 接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 08:10
 */
public interface Message {

    /**
     * 返回 Message 的类型
     *
     * @return Message 的类型
     */
    Class<? extends Message> getType();

}
