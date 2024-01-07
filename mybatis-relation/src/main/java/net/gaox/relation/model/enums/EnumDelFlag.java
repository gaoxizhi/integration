package net.gaox.relation.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaox·Eric
 * @date 2019/7/13 00:21
 */
@Getter
@AllArgsConstructor
public enum EnumDelFlag {
    /**
     * 被删除
     */
    DELETE(0, "删除"),
    NORMAL(1, "正常"),
    CANCEL(2, "注销");

    private Integer code;
    private String description;

}