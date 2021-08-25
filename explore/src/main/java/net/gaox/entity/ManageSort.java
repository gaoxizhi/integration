package net.gaox.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
 * <p> 排序测试类 </p>
 *
 * @author gaox·Eric
 * @date 2021/8/26 00:09
 */
@Data
@Accessors(chain = true)
public class ManageSort {
    private String type;
    private LocalDate time;
    private Date dateTime;
}
