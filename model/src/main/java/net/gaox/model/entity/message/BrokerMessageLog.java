package net.gaox.model.entity.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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


    @Override
    protected Serializable pkVal() {
        return this.messageId;
    }

}
