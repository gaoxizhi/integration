package net.gaox.excel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.gaox.excel.entity.Salary;

import java.util.List;

/**
 * <p> 工资 服务类 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
public interface SalaryService extends IService<Salary> {

    /**
     * 批量插入
     *
     * @param salaries list
     */
    void saveBatch(List<Salary> salaries);

}
