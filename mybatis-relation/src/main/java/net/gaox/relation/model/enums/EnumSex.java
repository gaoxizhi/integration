package net.gaox.relation.model.enums;

/**
 * @Description: <p>  </p>
 * @ClassName: EnumSex
 * @author gaox·Eric
 * @date 2019/7/13 00:32
 */

public enum EnumSex {

    /**
     * 被删除
     */
    FEMALE(0, "女"),
    MALE(1, "男"),
    CANCEL(2, "未知");

    private EnumSex(int code, String description) {
        this.code = new Integer(code);
        this.description = description;
    }

    private Integer code;
    private String description;

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
