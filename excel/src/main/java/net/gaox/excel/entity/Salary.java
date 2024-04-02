package net.gaox.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.gaox.excel.config.converter.LocalDateConverter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p> 工资表 entity </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@Data
@TableName("salary")
public class Salary {

    @ExcelProperty(value = "工号", index = 1)
    private String empNo;

    @ExcelProperty(value = "工资", index = 2)
    private BigDecimal salary;

    @TableField("from_date")
    @ExcelProperty(value = "开始时间", index = 3, converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate fromDate;

    @TableField("to_date")
    @ExcelProperty(value = "结束时间", index = 4, converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate toDate;

}
