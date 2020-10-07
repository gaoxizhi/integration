package net.gaox.lambda.clone;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/11/15 19:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@EqualsAndHashCode
class People implements Cloneable, Serializable {
    /**
     * id编号
     */
    private int id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 收入
     */
    private double inCome;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}