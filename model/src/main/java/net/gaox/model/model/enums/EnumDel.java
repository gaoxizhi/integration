package net.gaox.model.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum EnumDel implements IEnum {

    /**
     * 被删除
     */
    DELETE(false, "删除"),
    NORMAL(true, "正常");

    private Boolean value;
    private String desc;

    EnumDel(final Boolean value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

    /**
     * 重写ToString收字段字面值
     */
    @Override
    public String toString() {
        return desc;
    }
}