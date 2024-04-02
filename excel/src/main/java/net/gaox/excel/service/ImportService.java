package net.gaox.excel.service;

import com.alibaba.excel.EasyExcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.excel.entity.Salary;
import net.gaox.excel.listener.SalaryListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.Executor;


/**
 * <p> 导入Excel </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImportService {

    @Qualifier("task")
    private final Executor taskExecutor;
    private final SalaryListener salaryListener;

    public void importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Salary.class, salaryListener).doReadAll();
    }


    public void importExcelAsync(MultipartFile file) {
        // 开20个线程分别处理20个sheet，假设最多的sheet页数
        for (int i = 0; i < 20; i++) {
            int num = i;
            taskExecutor.execute(() -> {
                try {
                    EasyExcel.read(file.getInputStream(), Salary.class, salaryListener).sheet(num).doRead();
                } catch (IOException e) {
                    log.warn("sheet:{} read error", num);
                }
            });
        }
    }

}
