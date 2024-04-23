package net.gaox.controller;

import lombok.RequiredArgsConstructor;
import net.gaox.domain.model.entity.Order;
import net.gaox.message.fixed.KafkaRedoMessages;
import net.gaox.message.sender.MessageSender;
import net.gaox.model.dto.RedoDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p> 测试接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-10 11:49
 */

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private final MessageSender messageSender;
    private final KafkaRedoMessages kafkaRedoMessages;

    @GetMapping("/send")
    public Order send() {
        Order order = new Order();
        UUID uuid = UUID.randomUUID();
        String orderNumber = uuid.toString().replaceAll("-", "").substring(0, 16);
        order.setUserId(1L);
        order.setNumber(orderNumber);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        messageSender.sender(order);
        return order;
    }

    @PostMapping("/redo")
    public void redo(@RequestBody RedoDTO redo) {
        kafkaRedoMessages.redoMessages(redo.getTopics(), redo.getStartTime(), redo.getEndTime());
    }

}
