package net.gaox.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @since 2024-09-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Events extends Model<Events> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("event_time")
    private LocalDateTime eventTime;

    @TableField("location")
    private String location;

    @TableField("type")
    private String type;

    @TableField("participants")
    private String participants;

    @TableField(exist = false)
    private List<String> participantList;

    @TableField("result")
    private String result;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
