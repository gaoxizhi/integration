package net.gaox.relation.model.enums;

/**
 * @Description: <p>  </p>
 * @ClassName: EnumDelFlag
 * @Author: gaox·Eric
 * @Date: 2019/7/13 00:21
 */
public enum EnumDelFlag {
    /**
     * 被删除
     */
    DELETE(0, "删除"),
    NORMAL(1, "正常"),
    CANCEL(2, "注销");

    private EnumDelFlag(int code, String description) {
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
