package net.gaox.util.entity.yml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> yaml Node </p>
 *
 * @author gaox·Eric
 * @date 2023-03-27 01:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    /**
     * 层级关系
     */
    private Integer level;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 是否为空行
     */
    private Boolean emptyLine;

    /**
     * 当前行是否为有效配置
     */
    private Boolean effective;

    /**
     * 头部注释（单行注释）
     */
    private String headRemark;

    /**
     * 末尾注释
     */
    private String tailRemark;

    /**
     * 是否为最后一层配置
     */
    private Boolean last;

}
