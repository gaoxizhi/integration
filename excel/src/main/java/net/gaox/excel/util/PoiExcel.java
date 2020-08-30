package net.gaox.excel.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

/**
 * <p> 借鉴 hutool 解析过程 </p>
 *
 * @author gaox·Eric
 * @date 2019/9/21 23:26
 */
public class PoiExcel {

    public static void main(String[] args) throws Exception {
        String fileName = "/Users/gaox/codeing/aliyun" + File.separator + "ew.xls";
        Workbook work;
        try {
            work = new XSSFWorkbook(new FileInputStream(new File(fileName)));
        } catch (Exception e) {
            work = new HSSFWorkbook(new FileInputStream(new File(fileName)));
        }
        Sheet sheet = work.getSheetAt(0);
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                System.out.println(i + ":" + j + " --> " + getCellValue(row.getCell(j), null));
            }
        }
    }

    public static Object getCellValue(Cell cell, CellType cellType) {
        if (null == cell) {
            return null;
        }
        if (null == cellType) {
            cellType = cell.getCellType();
        }

        Object value;
        switch (cellType) {
            case NUMERIC:
                value = getNumericValue(cell);
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case FORMULA:
                // 遇到公式时查找公式结果类型
                value = getCellValue(cell, cell.getCachedFormulaResultType());
                break;
            case BLANK:
                value = "";
                break;
            case ERROR:
                final FormulaError error = FormulaError.forInt(cell.getErrorCellValue());
                value = (null == error) ? "" : error.getString();
                break;
            default:
                value = cell.getStringCellValue();
        }

        return value;
    }

    private static Object getNumericValue(Cell cell) {
        final double value = cell.getNumericCellValue();

        final CellStyle style = cell.getCellStyle();
        if (null == style) {
            return value;
        }

        final short formatIndex = style.getDataFormat();
        // 判断是否为日期
        if (isDateType(cell, formatIndex)) {
            // 使用Hutool的DateTime包装
            return cell.getDateCellValue();
        }
        final String format = style.getDataFormatString();
        // 普通数字
        if (null != format && format.indexOf('.') < 0) {
            final long longPart = (long) value;
            if (longPart == value) {
                // 对于无小数部分的数字类型，转为Long
                return longPart;
            }
        }
        return value;
    }

    private static boolean isDateType(Cell cell, int formatIndex) {
        // yyyy-MM-dd----- 14
        // yyyy年m月d日---- 31
        // yyyy年m月------- 57
        // m月d日 ---------- 58
        // HH:mm----------- 20
        // h时mm分 -------- 32
        if (formatIndex == 14 || formatIndex == 31 || formatIndex == 57 || formatIndex == 58 || formatIndex == 20 || formatIndex == 32) {
            return true;
        }
        if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
            return true;
        }
        return false;
    }
}
