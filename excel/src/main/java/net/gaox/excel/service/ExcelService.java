package net.gaox.excel.service;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> 实现层 </p>
 *
 * @author: gaox·Eric
 * @date: 2019-03-16 20:20:00
 */
@Service
public class ExcelService {

    final static String XLS = ".XLS";
    final static String XLSX = ".XLSX";

    /**
     * 处理上传的文件
     *
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public List getBankListByExcel(InputStream in, String fileName) throws Exception {
        List list = new ArrayList<>();
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        //表、行、单元格
        Sheet sheet;
        Row row;
        Cell cell;
        //从第一个表开始读取，直到最后一个sheet表
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            //跳过空的不存在的表
            if (sheet == null) {
                continue;
            }
            //从第一行开始读取，直到最后一行
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                //从第一个单元格开始读取，直到最后一个单元格
                //跳过空的数据行、跳过首行
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                //将单元格数据存入list
                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                //将行数据存入总体数据list
                list.add(li);
            }
        }
        work.close();
        return list;
    }

    /**
     * 判断文件格式
     *
     * @param inStr
     * @param fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook workbook;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (XLS.equals(fileType)) {
            workbook = new HSSFWorkbook(inStr);
        } else if (XLSX.equals(fileType)) {
            workbook = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("请上传excel文件！");
        }
        return workbook;
    }
}
