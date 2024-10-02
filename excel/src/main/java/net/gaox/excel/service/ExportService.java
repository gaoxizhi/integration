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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
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

    public void exportExcelByMock(Integer size, HttpServletResponse response) throws IOException {

        setExportHeader(response);
        Random random = new Random();
        Integer countSize = Optional.ofNullable(size).filter(s -> 0 < s).orElse(1_000_000);
        List<Salary> salaries = IntStream.range(0, countSize).mapToObj(i -> {
            String empNo = String.valueOf(i + 10_000);
            // 随机扰动 (太耗时了)
            // i += Math.abs(random.nextInt(100));
            int startMonth = (i % 12) + 1;
            int endMonth = (startMonth) % 12 + 1;
            int dayOfMonth = (i % 20) + 1;
            int startYear = 2018;
            int endYear = startYear + (endMonth == 1 ? 1 : 0);
            return Salary.builder()
                    .empNo(empNo)
                    .salary(new BigDecimal((i % 30) * 200 + 20_000))
                    .fromDate(LocalDate.of(startYear, startMonth, dayOfMonth))
                    .toDate(LocalDate.of(endYear, endMonth, dayOfMonth))
                    .build();
        }).collect(Collectors.toList());


        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), Salary.class).build()) {
            int threadCount = 20;
            List<Integer> pageSizeList = IntStream.range(0, threadCount)
                    // 每页取整后大小+是否增加大小
                    .mapToObj(i -> countSize / threadCount + (countSize % threadCount <= i ? 0 : 1))
                    .collect(Collectors.toList());

            IntStream.range(0, threadCount).forEach(i -> {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, String.format("部门%02d", i + 1)).build();
                Integer skipNumber = pageSizeList.subList(0, i).parallelStream().reduce(0, Integer::sum);
                List<Salary> salaryList = salaries.stream()
                        .skip(skipNumber).limit(pageSizeList.get(i))
                        .collect(Collectors.toList());
                // salaryMapper.insertBatch(salaryList);
                excelWriter.write(salaryList, writeSheet);
            });
        }
    }

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
            long size = count / list.size() + (0 == (count % list.size()) ? 0 : 1);

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
