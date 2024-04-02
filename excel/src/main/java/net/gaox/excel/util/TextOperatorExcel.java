package net.gaox.excel.util;

import cn.hutool.core.util.StrUtil;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * <p> jxl 操作 Excel </p>
 *
 * @author gaox·Eric
 * @date 2019-03-09 20:19
 */
public class TextOperatorExcel {

    public static void main(String[] args) {
        try {
            System.out.println("1.创建Excel文件，输入Excel文件名称（包括路径和后缀）");
            Scanner scan = new Scanner(System.in);
            // 获得键盘值
            final String fileNameInput = scan.next();
            String fileName = renameToXls(fileNameInput);
            // 调用生成Excel方法
            writeExcel(fileName);

            System.out.println("2.将内容写入Excel文件");
            // 调用将内容写入Excel方法
            writeContentToExcel(fileName);

            System.out.println("3.读取Excel文件");
            readExcelInfo(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成一个Excel文件
     */
    public static void writeExcel(String fileName) {
        WritableWorkbook wwb = null;
        try {
            // 创建一个可写入的工作薄(Workbook)对象
            wwb = Workbook.createWorkbook(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wwb != null) {
            // 创建一个可写入的工作表
            // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("sheet1", 0);
            // 循环添加单元格
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 5; j++) {
                    Label labelC = new Label(j, i, "这是第" + (i + 1) + "行，第"
                            + (j + 1) + "列");
                    try {
                        // 将生成的单元格添加到工作表中
                        ws.addCell(labelC);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            try {
                // 从内存中写入文件中
                wwb.write();
                // 从内存中写入文件中
                wwb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("生成一个Excel文件：" + fileName + "成功!");
    }

    /**
     * 将内容写入
     *
     * @param fileName 文件名
     * @throws Exception 异常
     */
    public static void writeContentToExcel(String fileName) throws Exception {
        File tempFile = new File(fileName);
        WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
        WritableSheet sheet = workbook.createSheet("TestCreateExcel", 0);
        // 一些临时变量，用于写到excel中
        Label l;
        jxl.write.Number n;
        jxl.write.DateTime d;

        // 预定义的一些字体和格式，同一个Excel中最好不要有太多格式  字形  大小  加粗 倾斜  下划线 颜色
        WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD,

                false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
        WritableCellFormat headerFormat = new WritableCellFormat(headerFont);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD,
                false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD,
                false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat detFormat = new WritableCellFormat(detFont);

        // 用于Number的格式
        NumberFormat nf = new NumberFormat("0.00000");
        WritableCellFormat priceFormat = new WritableCellFormat(detFont, nf);

        // 用于日期的
        DateFormat df = new DateFormat("yyyy-MM-dd");
        WritableCellFormat dateFormat = new WritableCellFormat(detFont, df);

        // 创建一些单元格，再加到sheet中
        l = new Label(0, 0, "用于测试的Excel文件", headerFormat);
        sheet.addCell(l);
        // 添加标题
        int column = 0;
        l = new Label(column++, 2, "标题", titleFormat);
        sheet.addCell(l);
        l = new Label(column++, 2, "日期", titleFormat);
        sheet.addCell(l);
        l = new Label(column++, 2, "货币", titleFormat);
        sheet.addCell(l);
        l = new Label(column++, 2, "价格", titleFormat);
        sheet.addCell(l);
        // 添加内容
        int i = 0;
        column = 0;
        l = new Label(column++, i + 3, "标题 " + i, detFormat);
        sheet.addCell(l);
        d = new DateTime(column++, i + 3, new java.util.Date(), dateFormat);
        sheet.addCell(d);
        l = new Label(column++, i + 3, "CNY", detFormat);
        sheet.addCell(l);
        n = new jxl.write.Number(column++, i + 3, 5.678, priceFormat);
        sheet.addCell(n);
        i++;
        column = 0;
        l = new Label(column++, i + 3, "标题 " + i, detFormat);
        sheet.addCell(l);
        d = new DateTime(column++, i + 3, new java.util.Date(), dateFormat);
        sheet.addCell(d);
        l = new Label(column++, i + 3, "SGD", detFormat);
        sheet.addCell(l);
        n = new jxl.write.Number(column++, i + 3, 98832, priceFormat);
        sheet.addCell(n);
        // 设置列的宽度
        column = 0;
        sheet.setColumnView(column++, 20);
        sheet.setColumnView(column++, 20);
        sheet.setColumnView(column++, 10);
        sheet.setColumnView(column++, 20);
        workbook.write();
        workbook.close();
        System.out.println("内容写入" + fileName + "成功");
    }

    public static void readExcelInfo(String fileName) throws Exception {
        // 构造Workbook（工作薄）对象
        Workbook book = Workbook.getWorkbook(new File(fileName));
        Sheet sheet = book.getSheet(0);
        // 得到列数
        int columNum = sheet.getColumns();
        int rowNum = sheet.getRows();
        System.out.println(columNum);
        System.out.println(rowNum);
        // 循环进行读写
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columNum; j++) {
                Cell cell1 = sheet.getCell(j, i);
                String result = cell1.getContents();
                System.out.print(result);
                System.out.print(" \t ");
            }
            System.out.println();
        }
        // 关闭（工作薄）对象
        book.close();
    }

    public static String renameToXls(String fileName) {
        // 参数校验
        if (StrUtil.isEmpty(fileName)) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        int lastDotIndex = fileName.lastIndexOf(".");
        // 处理文件名无点号的情况
        if (lastDotIndex == -1) {
            return fileName + ".xls";
        }

        // 获取扩展名并转为小写，判断是否为xls
        String extension = fileName.substring(lastDotIndex + 1).toLowerCase();
        if (!extension.equals("xls")) {
            // 检查是否以点号结尾，避免添加多余的点号
            return fileName.substring(0, lastDotIndex) + ".xls";
        }
        // 如果扩展名已经是xls，则返回原文件名
        return fileName;
    }

}
