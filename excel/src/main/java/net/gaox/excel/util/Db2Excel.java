package net.gaox.excel.util;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> 从数据库导出表数据结构及数据 </p>
 *
 * @author gaox·Eric
 * @date 2019/9/22 13:19
 */
public class Db2Excel {

    private static String dbName = "erp";

    private static Connection conn = null;

    static {
        try {
            // 注册数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&useUnicode=true";
            // 获取连接数据库的Connection对象
            conn = DriverManager.getConnection(url, "root", "root");
            System.out.println("数据库连接成功！" + conn);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException, IOException {

        XSSFWorkbook book = new XSSFWorkbook();
        Statement st = conn.createStatement();

        DatabaseMetaData dm = conn.getMetaData();
        ResultSet rs = dm.getTables(dbName, dbName, null, new String[]{"TABLE"});

        //	反模式: 我们以后开发时尽量不要采用结果集嵌套的方式写代码，尤其是带事务时，这很容易出问题的
/*        while (rs.next()) {
            //表格名
            String tableName = rs.getString("TABLE_NAME");
            //跨库查询
            String sql = "select * from " + dbName + "." + tableName;
            ResultSet rss = st.executeQuery(sql);
            while (rss.next()) {
                //.....
            }
        }*/

        List<String> tableNames = new ArrayList<>();
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            tableNames.add(tableName);
        }
        CellStyle style = book.createCellStyle();
        // Aqua background 表头填充背景颜色
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        //前景色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //位置：居中
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        //设置自动换行
        style.setWrapText(false);
        //上下左右，边框类型
        style.setBorderTop(BorderStyle.THICK);
        style.setBorderBottom(BorderStyle.THICK);
        style.setBorderLeft(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.THICK);

        //设置字体
        XSSFFont font = book.createFont();
        font.setFontName("仿宋_GB2312");
        //设置字体大小
        font.setFontHeightInPoints((short) 10);
        //粗体
        font.setBold(true);
        //选择需要用到的字体格式

        style.setFont(font);
        XSSFSheet summarizeSheet = book.createSheet("数据库概况");
        // 合并单元格：下标从0开始 起始行号，终止行号， 起始列号，终止列号
        summarizeSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
        //第一个参数代表列id(从0开始),第2个参数代表宽度值
        summarizeSheet.setColumnWidth(0, 3766);
        //冻结表头
        summarizeSheet.createFreezePane(0, 1, 0, 1);
        XSSFCell firstCell = summarizeSheet.createRow(0).createCell(0);
        firstCell.setCellValue("数据库概况");
        firstCell.setCellStyle(style);

        // 添加单元格注释
        // 创建XSSFPatriarch对象，XSSFPatriarch是所有注释的容器
        XSSFDrawing patr = summarizeSheet.createDrawingPatriarch();
        // 定义注释的大小和位置，详见文档
        XSSFComment comment = patr.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 0, 0, (short) 6, 5));
        // 设置注释内容
        comment.setString(new XSSFRichTextString("所有数据表概况"));
        // 设置注释作者. 当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("gaox");
        int line = 1;
        //处理数据库中的每一个表格
        for (String tableName : tableNames) {
            XSSFSheet sheet = book.createSheet(tableName);
            //冻结表头
            sheet.createFreezePane(0, 1, 0, 1);
            String sql = "select * from " + dbName + "." + tableName;
            ResultSet rss = st.executeQuery(sql);
            ResultSetMetaData rsmd = rss.getMetaData();
            //先获取字段数
            int cols = rsmd.getColumnCount();

            //setCellStyle(style)
            //导出 概括表
            XSSFRow sumTableHead = summarizeSheet.createRow(line++);
            XSSFCell cell1 = sumTableHead.createCell(0);
            cell1.setCellValue("数据库名");
            cell1.setCellStyle(style);
            sumTableHead.createCell(1).setCellValue(dbName);
            XSSFCell cell2 = sumTableHead.createCell(2);
            cell2.setCellValue("表名");
            cell2.setCellStyle(style);

            XSSFCell tableNameCell = sumTableHead.createCell(3);
            // 添加超链接 使用creationHelpper来创建XSSFHyperlink对象
            XSSFCreationHelper creationHelper = book.getCreationHelper();
            XSSFHyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
            hyperlink.setAddress("#'" + tableName + "'!A1");
            tableNameCell.setHyperlink(hyperlink);
            tableNameCell.setCellValue(tableName);

            XSSFCell cell3 = sumTableHead.createCell(4);
            cell3.setCellValue("列数");
            cell3.setCellStyle(style);
            sumTableHead.createCell(5).setCellValue(cols);

            XSSFRow sumHead = summarizeSheet.createRow(line++);
            sumHead.createCell(0).setCellValue("字段名称");
            sumHead.createCell(1).setCellValue("数据类型");
            sumHead.createCell(2).setCellValue("数据类型名");
            sumHead.createCell(3).setCellValue("对应数据类型的类");
            sumHead.createCell(4).setCellValue("最大字符个数");
            sumHead.createCell(5).setCellValue("别名");
            sumHead.createCell(6).setCellValue("列的模式");
            sumHead.createCell(7).setCellValue("类型的长度");
            sumHead.createCell(8).setCellValue("小数点后的位数");
            sumHead.createCell(9).setCellValue("自动递增");
            sumHead.createCell(10).setCellValue("货币类型");
            sumHead.createCell(11).setCellValue("可为空");
            sumHead.createCell(12).setCellValue("只读");
            sumHead.createCell(13).setCellValue("出现在where中");
            for (int cellIndex = sumHead.getFirstCellNum(); cellIndex < sumHead.getLastCellNum(); cellIndex++) {
                sumHead.getCell(cellIndex).setCellStyle(style);
                //自适应宽度，全局
//                if (0 != cellIndex) {
//                    summarizeSheet.autoSizeColumn(cellIndex);
//                }

                //根据表头自适应宽度，单行
//                int columnWidth = summarizeSheet.getColumnWidth(cellIndex) / 256;
                int length = sumHead.getCell(cellIndex).toString().getBytes("GBK").length;
//                if (columnWidth < length + 1) {
//                    columnWidth = length + 8;
//                }
                summarizeSheet.setColumnWidth(cellIndex, length * 256);
            }
            //导出详细表头
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < cols; i++) {
                //自动宽度，首行
                sheet.autoSizeColumn(i);
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(rsmd.getColumnName(i + 1));
                XSSFRow sumRow = summarizeSheet.createRow(line++);
                int j = i + 1;
                sumRow.createCell(0).setCellValue(rsmd.getColumnName(j));
                sumRow.createCell(1).setCellValue(rsmd.getColumnType(j));
                sumRow.createCell(2).setCellValue(rsmd.getColumnTypeName(j));
                sumRow.createCell(3).setCellValue(rsmd.getColumnClassName(j));
                sumRow.createCell(4).setCellValue(rsmd.getColumnDisplaySize(j));
                sumRow.createCell(5).setCellValue(rsmd.getColumnLabel(j));
                sumRow.createCell(6).setCellValue(rsmd.getSchemaName(j));
                sumRow.createCell(7).setCellValue(rsmd.getPrecision(j));
                sumRow.createCell(8).setCellValue(rsmd.getScale(j));
                sumRow.createCell(9).setCellValue(rsmd.isAutoIncrement(j));
                sumRow.createCell(10).setCellValue(rsmd.isCurrency(j));
                sumRow.createCell(11).setCellValue(rsmd.isNullable(j));
                sumRow.createCell(12).setCellValue(rsmd.isReadOnly(j));
                sumRow.createCell(13).setCellValue(rsmd.isSearchable(j));
            }
            summarizeSheet.createRow(line++);
            summarizeSheet.createRow(line++);
            //导出数据
            int idx = 1;
            while (rss.next()) {
                row = sheet.createRow(idx++);
                for (int i = 0; i < cols; i++) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellValue(rss.getString(i + 1));
                }
            }
        }

        line += 5;
        XSSFRow bottomRow = summarizeSheet.createRow(line);
        summarizeSheet.addMergedRegion(new CellRangeAddress(line, line, 0, 13));
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
        XSSFHyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
        hyperlink.setAddress("http://gaozhaoxi.com/");
        links.setHyperlink(hyperlink);
        links.setCellValue("光而不耀 gaozhaoxi.com");

        //把工作簿对象book序列化
        book.write(new FileOutputStream("/Users/gaox/codeing/aliyun" + File.separator + dbName + ".xlsx"));
        System.out.println("导出成功！");
    }
}
