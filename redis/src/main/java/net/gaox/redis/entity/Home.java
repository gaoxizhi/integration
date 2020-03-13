package net.gaox.redis.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 家庭表
 * </p>
 *
 * @author gaox
 * @since 2019-04-07
 */
@Data
@Accessors(chain = true)
public class Home implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long createUserId;
    private String alias;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer able;
}