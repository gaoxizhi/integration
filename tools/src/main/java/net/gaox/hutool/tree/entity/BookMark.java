package net.gaox.hutool.tree.entity;

import lombok.Data;

/**
 * <p> 书签 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-12 08:38
 */
@Data
public class BookMark {
    private Long id;
    private Long parentId;
    private String name;
    private String url;
    private String title;
    private String icon;
    private Integer sort;
    private Integer status;
    private Integer type;
    private String remark;
}
