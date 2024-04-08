package net.gaox.model.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 状态枚举，实现 IEnum 接口 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@Getter
@AllArgsConstructor
public enum EnumDel implements IEnum<Integer> {

    /**
     * 被删除
     */
    DELETE(0, "删除"),
    NORMAL(1, "正常"),
    CANCEL(2, "注销"),
    ;

    private Integer value;
    private String desc;

    /**
     * 重写ToString显示字段字面值
     */
    @Override
    public String toString() {
        return desc;
    }
}