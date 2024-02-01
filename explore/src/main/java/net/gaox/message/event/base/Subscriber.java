package net.gaox.message.event.base;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * <p> 订阅对象信息 </p>
 * 一个对象实例可能被封装成多个 subscriber
 *
 * @author gaox·Eric
 * @date 2024-02-01 11:49
 */
@Getter
public class Subscriber {

    /**
     * 对象实例
     */
    private final Object subscribeObject;

    /**
     * Subscribe 注解的方法
     */
    private final Method subscribeMethod;

    /**
     * 是否禁用订阅
     */
    @Setter
    private boolean disable = false;

    public Subscriber(Object subscribeObject, Method subscribeMethod) {
        this.subscribeObject = subscribeObject;
        this.subscribeMethod = subscribeMethod;
    }

}
