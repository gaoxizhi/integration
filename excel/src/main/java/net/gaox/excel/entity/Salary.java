package net.gaox.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
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
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("salary")
public class Salary {

    @ExcelProperty(value = "工号")
    private String empNo;

    @ExcelProperty(value = "工资")
    private BigDecimal salary;

    @TableField("from_date")
    @ExcelProperty(value = "开始时间", converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @TableField("to_date")
    @ExcelProperty(value = "结束时间", converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

}
