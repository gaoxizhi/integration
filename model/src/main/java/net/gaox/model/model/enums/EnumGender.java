package net.gaox.model.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum EnumGender implements IEnum {

    /**
     * 被删除
     */
    FEMALE(false, "女人"),
    MALE(true, "男人");

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
}