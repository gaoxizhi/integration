package net.gaox.domain.model.entity.message;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.gaox.domain.model.entity.Order;
import net.gaox.domain.model.enums.OrderSendStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p> 订单处理记录表 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BrokerMessageLog对象", description = "订单处理记录表")
public class BrokerMessageLog extends Model<BrokerMessageLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "message_id", type = IdType.INPUT)
    private String messageId;

    @TableField("message")
    private String message;

    @TableField("try_count")
    private Integer tryCount;

    @TableField("status")
    private String status;

    @TableField("next_retry")
    private LocalDateTime nextRetry;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 订单消息
     *
     * @param order 订单
     * @return 消息
     */
    public static BrokerMessageLog fromOrder(Order order) {
        BrokerMessageLog log = new BrokerMessageLog();
        log.setMessageId(String.valueOf(order.getNumber()));
        log.setMessage(JSONObject.toJSONString(order));
        log.setStatus(OrderSendStatusEnum.ORDER_SENDING.getCode());
        LocalDateTime now = LocalDateTime.now();
        log.setCreateTime(now);
        log.setNextRetry(now.plusMinutes(1));
        log.setTryCount(0);
        return log;
    }

    @Override
    protected Serializable pkVal() {
        return this.messageId;
    }

}
