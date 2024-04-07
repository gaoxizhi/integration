package net.gaox.relation.mapper;

import lombok.extern.slf4j.Slf4j;
import net.gaox.relation.model.dto.OrderAndOrderDetailsDTO;
import net.gaox.relation.model.dto.OrderAndUserDTO;
import net.gaox.relation.model.dto.OrderUserDTO;
import net.gaox.relation.model.dto.UserAndOrderItemDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <p> Mapping 关系映射测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-07 22:55
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderCustomMapperTest {

    @Resource
    private OrderCustomMapper orderCustomMapper;

    @Test
    public void testFindOrderUser() {
        List<OrderUserDTO> orderUsers = orderCustomMapper.findOrderUser();
        Optional.ofNullable(orderUsers).ifPresent(list -> list.forEach(s -> log.info("list: {}", s)));
    }

    @Test
    public void testFindOrderUserResultMap() {
        List<OrderAndUserDTO> orders = orderCustomMapper.findOrderUserResultMap();
        Optional.ofNullable(orders).ifPresent(list -> list.forEach(s -> log.info("list: {}", s)));
    }

    @Test
    public void testFindOrderAndOrderDetailResultMap() {
        List<OrderAndOrderDetailsDTO> orderRelations = orderCustomMapper.findOrderAndOrderDetailResultMap();
        Optional.ofNullable(orderRelations).ifPresent(list -> list.forEach(s -> log.info("list: {}", s)));
    }

    @Test
    public void testFindUserAndItemResultMap() {
        List<UserAndOrderItemDTO> userOrderRelations = orderCustomMapper.findUserAndItemResultMap();
        Optional.ofNullable(userOrderRelations).ifPresent(list -> list.forEach(s -> log.info("list: {}", s)));
    }

}
