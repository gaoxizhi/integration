package net.gaox.relation.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaox·Eric
 * @date 2019/7/13 00:32
 */
@Getter
@AllArgsConstructor
public enum EnumSex implements ICodeEnum {

    /**
     * 被删除
     */
    FEMALE(0, "女"),
    MALE(1, "男"),
    CANCEL(2, "未知");

    private Integer code;
    private String description;

}