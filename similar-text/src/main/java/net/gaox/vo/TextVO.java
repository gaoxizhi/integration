package net.gaox.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p> 比对文本结果 vo </p>
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
public class TextVO {
    private String textA;
    private String textB;

    /**
     * 相似度
     */
    private Double similarity;
}
