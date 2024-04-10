package net.gaox.message.sender;

import lombok.extern.slf4j.Slf4j;
import net.gaox.domain.model.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;


/**
 * <p> Kafka消息发送测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-09 21:42
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageSenderTest {

    @Resource
    private MessageSender messageSender;

    @Resource
    private TransactionMessageSender transactionMessageSender;

    @Test
    public void sender() {
        Order order = new Order();
        UUID uuid = UUID.randomUUID();
        String orderNumber = uuid.toString().replaceAll("-", "").substring(0, 16);
        order.setUserId(1L);
        order.setNumber(orderNumber);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        messageSender.sender(order);
    }


    @Test
    public void senderTopicByOrderNumber() {
        Order order = new Order();
        UUID uuid = UUID.randomUUID();
        String orderNumber = uuid.toString().replaceAll("-", "").substring(0, 16);
        order.setUserId(1L);
        order.setNumber(orderNumber);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        messageSender.senderTopicByOrderNumber(order);
    }


    @Test
    public void sendMessageInTransaction() {
        Order order = new Order();
        UUID uuid = UUID.randomUUID();
        String orderNumber = uuid.toString().replaceAll("-", "").substring(0, 16);
        order.setUserId(1L);
        order.setNumber(orderNumber);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        transactionMessageSender.sendMessageInTransaction(Collections.singletonList(order));
    }

}
