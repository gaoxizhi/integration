package net.gaox.conf.serializer.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p> 俱乐部 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-07 17:28
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
public class Club implements Serializable {
    private Integer id;
    private String name;
    private String info;
    private LocalDateTime createTime;
    private Integer rank;
}
