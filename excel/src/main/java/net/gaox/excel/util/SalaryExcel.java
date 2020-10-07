package net.gaox.excel.util;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * <p> 导出带公式表格 </p>
 *
 * @author gaox·Eric
 * @date 2019/9/22 13:19
 */
public class SalaryExcel {

    public static void main(String[] args) throws IOException {

        XSSFWorkbook book = new XSSFWorkbook();

        CellStyle style = book.createCellStyle();
        //前景色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //位置：居中
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        //设置字体
        XSSFFont font = book.createFont();
        font.setFontName("仿宋_GB2312");
        //设置字体大小
        font.setFontHeightInPoints((short) 10);
        //粗体
        font.setBold(true);
        //选择需要用到的字体格式

        style.setFont(font);
        XSSFSheet sheet = book.createSheet("工资表");
        // 合并单元格：下标从0开始 起始行号，终止行号， 起始列号，终止列号
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        //第一个参数代表列id(从0开始),第2个参数代表宽度值
        sheet.setColumnWidth(0, 3766);
        //冻结表头
        sheet.createFreezePane(0, 1, 0, 1);
        XSSFCell firstCell = sheet.createRow(0).createCell(0);
        firstCell.setCellValue("2020年8月薪水");
        firstCell.setCellStyle(style);

        int line = 1;
        XSSFRow firstRow = sheet.createRow(line++);

        firstRow.createCell(0).setCellValue("姓名");
        firstRow.createCell(1).setCellValue("应出勤");
        firstRow.createCell(2).setCellValue("出勤天");
        firstRow.createCell(3).setCellValue("基本工资");
        firstRow.createCell(4).setCellValue("出勤率");
        firstRow.createCell(5).setCellValue("实际工资");
        firstRow.createCell(6).setCellValue("签字");

        // 设置两位小数，千分位
        CellStyle dataStyle = book.createCellStyle();
        dataStyle.setDataFormat(book.createDataFormat().getFormat("#,##0.00"));

        Random random = new Random(100);
        for (int i = 0; i < 10; i++) {
            XSSFRow row = sheet.createRow(line++);
            row.createCell(0, CellType.STRING).setCellValue(new RandomName().getRandomName());
            row.createCell(1).setCellValue(31);
            row.createCell(2).setCellValue(random.nextInt(10) + 10);
            row.createCell(3).setCellValue(random.nextInt(60) * 100 + 5000);
            // Formula 设置公式，不允许带'='
            XSSFCell cell1 = row.createCell(4);
            cell1.setCellStyle(dataStyle);
            cell1.setCellFormula("C" + line + "/B" + line);

            XSSFCell cell2 = row.createCell(5);
            cell2.setCellStyle(dataStyle);
            cell2.setCellFormula("D" + line + "*E" + line);
        }

        XSSFRow bottomRow = sheet.createRow(line);
        sheet.addMergedRegion(new CellRangeAddress(line, line, 0, 6));
        CellStyle bottomCellStyle = book.createCellStyle();
        bottomCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        // 设置字体样式 颜色、下划线
        XSSFFont bottomFont = book.createFont();
        bottomFont.setUnderline((byte) 1);
        bottomFont.setColor(IndexedColors.BLUE.index);
        bottomCellStyle.setFont(bottomFont);

        XSSFCell links = bottomRow.createCell(0);
        links.setCellStyle(bottomCellStyle);
        XSSFCreationHelper creationHelper = book.getCreationHelper();
        XSSFHyperlink hyperlink = (XSSFHyperlink) creationHelper.createHyperlink(HyperlinkType.URL);
        hyperlink.setAddress("http://gaozhaoxi.com/");
        links.setHyperlink(hyperlink);
        links.setCellValue("光而不耀 gaozhaoxi.com");

        //把工作簿对象book序列化
        book.write(new FileOutputStream("/Users/gaox/codeing/aliyun" + File.separator + "salary.xlsx"));
        System.out.println("导出成功！");
    }
}
