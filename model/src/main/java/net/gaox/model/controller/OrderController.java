package net.gaox.model.controller;


import lombok.RequiredArgsConstructor;
import net.gaox.domain.model.entity.Order;
import net.gaox.model.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p> 订单表 前端控制器 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Order postOrderTest() {
        Order order = new Order();
        UUID uuid = UUID.randomUUID();
        String orderNumber = uuid.toString().replaceAll("-", "").substring(0, 16);
        order.setUserId(1L);
        order.setNumber(orderNumber);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderService.createOrder(order);

        return order;
    }

}