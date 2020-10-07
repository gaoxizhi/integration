package net.gaox.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p> 文件及图片类型附件 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/24 21:38
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Accessory {
    /**
     * 附件类型
     */
    private AccessoryType type;
    /**
     * 附件id
     */
    private String pictureId;
    /**
     * 附件路径
     */
    private String filePath;
}