package net.gaox.redis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 体感温度湿度计
 * </p>
 *
 * @author gaox
 * @since 2019-04-08
 */
@Data
@Accessors(chain = true)
public class SomaTempLog implements Serializable {

    private static final long serialVersionUID = 14646897L;

    private Long id;
    private Long eqId;
    private LocalDateTime updateTime;
    private Float temp;
    private Float hum;
}