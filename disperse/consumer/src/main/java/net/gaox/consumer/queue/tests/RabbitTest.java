package net.gaox.consumer.queue.tests;

import lombok.extern.slf4j.Slf4j;
import net.gaox.common.constant.Constants;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p> Rabbit 对应测试消费者 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-02 01:35
 */
@Slf4j
@Component
public class RabbitTest {

    /**
     * 直连队列
     * 设置Queue的exclusive=true，队列只对当前消费者可见，其他消费者不可见
     */
    @RabbitListener(queuesToDeclare = @Queue(Constants.RABBIT_QUEUE))
    public void receive(String message) {
        log.info("receive = {}", message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void work1(String message) {
        log.info("work1 message = {}", message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void work2(String message) {
        log.info("work2 message = {}", message);
    }

    /**
     * 多播模型 Exchange的type = fanout
     * Queue没有参数时，会创建一个临时队列
     * Exchange的type，指定路由模式
     *
     * @param message 消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "logs", type = "fanout")
    ))
    public void fanout1(String message) {
        log.info("fanout1 message = {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "logs", type = "fanout")
    ))
    public void fanout2(String message) {
        log.info("fanout2 message = {}", message);
    }

    /**
     * 路由模型
     * QueueBinding的key，指定路由key
     * Exchange的type，指定direct 直连
     *
     * @param message 消息
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(),
            key = {"info", "error"},
            exchange = @Exchange(type = "direct", name = "directs")
    )})
    public void route1(String message) {
        log.info("route1 message = {}", message);
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(),
            key = {"error"},
            exchange = @Exchange(type = "direct", name = "directs")
    )})
    public void route2(String message) {
        log.info("route2 message = {}", message);
    }

    /**
     * 订阅模型-topic
     * QueueBinding的key，指定 通配符
     * Exchange的type，指定topic
     *
     * @param message 消息
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue,
            key = {"user.*"},
            exchange = @Exchange(type = "topic", name = "topics")
    )})
    public void topic1(String message) {
        log.info("topic1 message = {}", message);
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue,
            key = {"user.#"},
            exchange = @Exchange(type = "topic", name = "topics")
    )})
    public void topic2(String message) {
        log.info("topic2 message = {}", message);
    }

}
