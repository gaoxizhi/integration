package net.gaox.model.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.domain.model.entity.Order;
import net.gaox.domain.model.entity.message.BrokerMessageLog;
import net.gaox.domain.model.enums.OrderSendStatusEnum;
import net.gaox.model.events.OrderSendEvent;
import net.gaox.model.mapper.BrokerMessageLogMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <p> 订单处理重试任务 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 12:20
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RetrySendTask {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 系统启动后5秒开启定时任务 10秒执行一次
     */
    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void rabbitmqReSend() {
        LambdaQueryWrapper<BrokerMessageLog> queryWrapper = new LambdaQueryWrapper<BrokerMessageLog>()
                .le(BrokerMessageLog::getNextRetry, LocalDateTime.now())
                .eq(BrokerMessageLog::getStatus, OrderSendStatusEnum.ORDER_SENDING.getCode());

        /**
         *
         * 查询出下一次执行时间小于当前时间的日志记录并且状态为投递中，
         * 遍历结果集，判断重试次数是或大于3次，如果大于，将日志设置为投递失败，
         * 如果小于 则尝试重新投递，并改变数据库中日志的尝试次数
         *
         */
        brokerMessageLogMapper.selectList(queryWrapper).forEach(messageLog -> {
            if (messageLog.getTryCount() >= 3) {
                messageLog.setStatus(OrderSendStatusEnum.ORDER_SEND_FAILURE.getCode());
                messageLog.setUpdateTime(LocalDateTime.now());
                // 测试不消费事件
                Order order = JSONObject.parseObject(messageLog.getMessage(), Order.class);
                OrderSendEvent even = new OrderSendEvent(OrderSendStatusEnum.ORDER_SEND_FAILURE, order);
                applicationEventPublisher.publishEvent(even);
                brokerMessageLogMapper.updateById(messageLog);
            } else {
                messageLog.setTryCount(messageLog.getTryCount() + 1);
                messageLog.setUpdateTime(LocalDateTime.now());
                brokerMessageLogMapper.updateById(messageLog);
                try {
                    //重新投递信息
                    Order order = JSONObject.parseObject(messageLog.getMessage(), Order.class);
                    OrderSendEvent even = new OrderSendEvent(OrderSendStatusEnum.ORDER_SENDING, order);
                    applicationEventPublisher.publishEvent(even);
                    log.info("订单信息重新投递成功，订单号：{}", messageLog.getMessageId());
                } catch (Exception e) {
                    log.error("订单信息重新投递失败，订单号：{}", messageLog.getMessageId());
                }
            }
        });
    }

}
