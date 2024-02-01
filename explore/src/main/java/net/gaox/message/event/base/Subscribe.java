package net.gaox.message.event.base;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> 指定接收消息时的回调方法的注解 </p>
 * 注册对象给 Event Bus 的时候需要指定接收消息时的回调方法，我们采用注解的方式进行指定 Event 回调方法
 *
 * @author gaox·Eric
 * @date 2024-02-01 10:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {

    /**
     * 定义默认的 topic 值
     */
    String DEFAULT_TOPIC = "default-topic";

    /**
     * 指定topic, 默认 default-topic
     *
     * @return topic
     */
    @AliasFor(value = "value")
    String topic() default DEFAULT_TOPIC;

    @AliasFor(value = "topic")
    String value() default DEFAULT_TOPIC;

}
