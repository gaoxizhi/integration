package net.gaox.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

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
public class People implements Cloneable, Serializable {
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

    /**
     * 测试list
     */
    public static List<People> list = Arrays.asList(
            People.builder().id(101).name("张三").inCome(5000).build(),
            People.builder().id(102).name("李四").inCome(8000).build(),
            People.builder().id(103).name("王五").inCome(3000).build(),
            People.builder().id(102).name("李四").inCome(2000).build(),
            People.builder().id(103).name("王五").inCome(4500).build()
    );
}