package net.gaox.excel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.gaox.excel.entity.Salary;
import net.gaox.excel.mapper.SalaryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


/**
 * <p> 导出 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@Slf4j
@Service
public class ExportService {
    public static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Resource
    private SalaryMapper salaryMapper;

    public void exportBasicExcel(HttpServletResponse response) throws IOException {

        setExportHeader(response);

        LambdaQueryWrapper<Salary> queryWrapper = null;
        // new LambdaQueryWrapper<Salary>().last("limit 100");
        List<Salary> salaries = salaryMapper.selectList(queryWrapper);

        EasyExcel.write(response.getOutputStream(), Salary.class).sheet().doWrite(salaries);
    }


    public void exportSheetExcel(HttpServletResponse response) throws IOException {

        setExportHeader(response);

        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), Salary.class).build()) {
            LambdaQueryWrapper<Salary> queryWrapper = null;
            // new LambdaQueryWrapper<Salary>().eq(Salary::getEmpNo, "41612");
            Long count = salaryMapper.selectCount(queryWrapper);
            List<String> list = Arrays.asList("董事", "财务", "生产", "检验", "保卫", "保洁", "食堂");
            Long size = count / list.size();

            IntStream.range(0, list.size()).forEach(i -> {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, list.get(i)).build();
                Page<Salary> selectPage = salaryMapper.selectPage(new Page<>(i + 1, size), queryWrapper);
                excelWriter.write(selectPage.getRecords(), writeSheet);
            });
        }
    }

    @SneakyThrows
    private static void setExportHeader(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String fileName = URLEncoder.encode("工资表.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
    }

}
