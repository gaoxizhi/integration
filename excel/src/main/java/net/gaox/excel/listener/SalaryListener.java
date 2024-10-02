package net.gaox.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.excel.entity.Salary;
import net.gaox.excel.service.SalaryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> 工资表 导入监听器 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SalaryListener implements ReadListener<Salary> {

    @Qualifier("task")
    private final Executor taskExecutor;
    private final SalaryService salaryService;

    private ThreadLocal<ArrayList<Salary>> salariesList = ThreadLocal.withInitial(ArrayList::new);
    private static AtomicInteger count = new AtomicInteger(1);
    private static final int batchSize = 1000;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invoke(Salary data, AnalysisContext context) {
        salariesList.get().add(data);
        if (salariesList.get().size() >= batchSize) {
            asyncSaveData();
        }
    }

    public void saveData() {
        if (!salariesList.get().isEmpty()) {
            salaryService.saveBatch(salariesList.get());
            log.info("第{}次插入{}条数据", count.getAndAdd(1), salariesList.get().size());
            salariesList.get().clear();
        }
    }

    public void asyncSaveData() {
        if (!salariesList.get().isEmpty()) {
            ArrayList<Salary> salaries = (ArrayList<Salary>) salariesList.get().clone();
            taskExecutor.execute(new SaveTask(salaries, salaryService));
            salariesList.get().clear();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("一个Sheet全部处理完");
        if (salariesList.get().size() >= batchSize) {
            saveData();
        }
    }

    static class SaveTask implements Runnable {

        private List<Salary> salariesList;
        private SalaryService salaryService;

        public SaveTask(List<Salary> salariesList, SalaryService salaryService) {
            this.salariesList = salariesList;
            this.salaryService = salaryService;
        }

        @Override
        public void run() {
            salaryService.saveBatch(salariesList);
            log.info("第{}次插入{}条数据", count.getAndAdd(1), salariesList.size());
        }
    }

}
