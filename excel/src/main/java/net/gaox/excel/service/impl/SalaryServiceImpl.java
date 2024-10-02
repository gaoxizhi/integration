package net.gaox.excel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import net.gaox.excel.entity.Salary;
import net.gaox.excel.mapper.SalaryMapper;
import net.gaox.excel.service.SalaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 工资表 实现类 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@Service
@RequiredArgsConstructor
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {
    private final SalaryMapper salaryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(List<Salary> salaries) {
        salaryMapper.insertBatch(salaries);
    }

}
