package net.gaox.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.gaox.excel.entity.Salary;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;


/**
 * <p> 工资表 Mapper 接口 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@Mapper
public interface SalaryMapper extends BaseMapper<Salary> {

    int insertBatch(Collection<Salary> salaries);

}
