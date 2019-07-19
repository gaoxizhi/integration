package net.gaox.model.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum EnumGender implements IEnum {

    /**
     * 被删除
     */
    FEMALE(false, "女人"),
    MALE(true, "男人"),
    UN_KNOW(null,"未知");

    private Boolean value;
    private String desc;

    EnumGender(final Boolean value, final String desc) {
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
//        return null == this ? "UN_KNOW" : this.name();
        return desc;
    }
}