package net.gaox.relation.mapper;

import lombok.extern.slf4j.Slf4j;
import net.gaox.relation.model.dto.OrderAndOrderDetailsDTO;
import net.gaox.relation.model.dto.UserAndOrderItemDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <p> 注解关联查询 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-07 20:35
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTest {

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void findOrdersUser() {
        List<UserAndOrderItemDTO> allUserOrders = orderMapper.findAllUserOrders();

        Optional.ofNullable(allUserOrders).ifPresent(userList ->
                userList.forEach(user -> {
                    log.info("user:{}", user);
                    Optional.ofNullable(user).map(UserAndOrderItemDTO::getOrderList).ifPresent(ordersList ->
                            ordersList.forEach(orders -> {
                                log.info("orders:{}", orders);
                                Optional.ofNullable(orders).map(OrderAndOrderDetailsDTO::getOrderDetails).ifPresent(orderDetails ->
                                        orderDetails.forEach(orderDetail -> {
                                            log.info("orderDetail:{}", orderDetail);
                                            log.info("item:{}", orderDetail.getItem());
                                        })
                                );
                            })
                    );
                })
        );
    }

    @Test
    public void findOrdersByUserId() {
        List<OrderAndOrderDetailsDTO> orders = orderMapper.findOrdersByUserId(1L);

        Optional.ofNullable(orders).ifPresent(ordersList ->
                ordersList.forEach(order -> {
                    log.info("orders:{}", order);
                    Optional.ofNullable(order).map(OrderAndOrderDetailsDTO::getOrderDetails).ifPresent(orderDetails ->
                            orderDetails.forEach(orderDetail -> {
                                log.info("orderDetail:{}", orderDetail);
                                log.info("item:{}", orderDetail.getItem());
                            })
                    );
                })
        );
    }

}
