package net.gaox.model.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 性别枚举，实现 IEnum 接口 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@Getter
@AllArgsConstructor
public enum EnumGender implements IEnum<Integer> {

    /**
     * 被删除
     */
    FEMALE(1, "女人"),
    MALE(2, "男人"),
    UN_KNOW(0, "未知");

    private Integer value;
    private String desc;

    /**
     * 重写ToString收字段字面值
     */
    @Override
    public String toString() {
//        return null == this ? "UN_KNOW" : this.name();
        return desc;
    }
}