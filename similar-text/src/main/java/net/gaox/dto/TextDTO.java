package net.gaox.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p> 比对文本 dto </p>
 *
 * @author gaox·Eric
 * @date 2023-04-17 20:50
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@EqualsAndHashCode
public class TextDTO {
    private String textA;
    private String textB;
}
