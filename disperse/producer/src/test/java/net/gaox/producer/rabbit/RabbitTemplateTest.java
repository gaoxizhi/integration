package net.gaox.producer.rabbit;

import net.gaox.common.constant.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * <p> Rabbit 测试生产者 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-02 01:26
 */
@SpringBootTest
public class RabbitTemplateTest {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 直连 P->1-1->C (一个队列)
     * 要求队列只有一个消费者
     */
    @Test
    public void testDirect() {
        rabbitTemplate.convertAndSend(Constants.RABBIT_QUEUE, "hello world from test1");
        rabbitTemplate.convertAndSend(Constants.RABBIT_QUEUE, "hello world from test2");
    }

    /**
     * 任务模型 P->1-m->C (同一个队列)
     */
    @Test
    public void testWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", i + ". hello work!");
        }
    }

    /**
     * 广播模式 扇出  P->1-m->C (每个消费者都有一个完整队列)
     */
    @Test
    public void testFanout() {
        rabbitTemplate.convertAndSend("logs", "", "这是日志广播1");
        rabbitTemplate.convertAndSend("logs", "", "这是日志广播2");
    }

    /**
     * 订阅模型-Direct(直连) P->1-m->C (同一消息，根据路由发给不同队列)
     */
    @Test
    public void testSubscribeDirect() {
        // 第二个参数，routingKey = 路由键
        rabbitTemplate.convertAndSend("directs", "info", "01 info 的日志信息");
        rabbitTemplate.convertAndSend("directs", "error", "02 error 的日志信息");
        rabbitTemplate.convertAndSend("directs", "error", "03 error 的日志信息");
        rabbitTemplate.convertAndSend("directs", "trace", "04 trace 的日志信息");
        rabbitTemplate.convertAndSend("directs", "info", "05 info 的日志信息");
        rabbitTemplate.convertAndSend("directs", "debug", "06 debug 的日志信息");
        rabbitTemplate.convertAndSend("directs", "error", "07 error 的日志信息");
    }

    /**
     * 订阅模式-Topic  P->1-m->C (同一消息，根据路由发给不同(订阅者)通配符的队列)
     */
    @Test
    public void testTopic() {
        rabbitTemplate.convertAndSend("topics", "user.findAll", "user.findAll 的消息");
        rabbitTemplate.convertAndSend("topics", "user.find1", "user.find1 的消息");
        rabbitTemplate.convertAndSend("topics", "user.save.findAll", "user.save.findAll 的消息");
        rabbitTemplate.convertAndSend("topics", "user.update.id1", "user.update.id1 的消息");
    }

}
