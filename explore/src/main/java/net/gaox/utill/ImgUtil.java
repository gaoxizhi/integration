package net.gaox.utill;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * <p> 图片生成工具 </p>
 *
 * @author gaox·Eric
 * @date 2020/11/26 22:43
 */

public class ImgUtil {
    public static void main(String[] args) throws Exception {

        createImage("请A1003到3号窗口", new Font("宋体", Font.BOLD, 30), new File(
                "file/a.png"), 4096, 64);
        ImgUtil img = new ImgUtil();
        try {
            String[][] tableData1 = {{"8月31日", "累计用户数", "目标值", "完成进度", "时间进度", "进度差异"}, {"掌厅客户端（户）", "469281", "1500000", "31.2%", "33.6%", "-2.4%"}};
            String[][] tableData2 = {{"8月31日（户）", "新增用户数", "日访问量", "累计用户数", "环比上月"},
                    {"合肥和巢湖", "469281", "1500000", "31.2%", "33.6%"},
                    {"芜湖", "469281", "1500000", "31.2%", "33.6%"},
                    {"蚌埠", "469281", "1500000", "31.2%", "33.6%"},
                    {"淮南", "469281", "1500000", "31.2%", "33.6%"},
                    {"马鞍山", "469281", "1500000", "31.2%", "33.6%"},
                    {"淮北", "469281", "1500000", "31.2%", "33.6%"}};
            img.myGraphicsGeneration(tableData2, "file/myPic.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成图片
     *
     * @param cellsValue 以二维数组形式存放 表格里面的值
     * @param path       文件保存路径
     */
    public void myGraphicsGeneration(String cellsValue[][], String path) {
        // 字体大小
        int fontTitleSize = 32;
        // 横线的行数
        int totalRow = cellsValue.length + 1;
        // 竖线的行数
        int totalCol = 0;
        if (cellsValue[0] != null) {
            totalCol = cellsValue[0].length;
        }
        // 图片宽度
        int imageWidth = 1024;
        // 行高
        int rowHeight = 50;
        // 图片高度
        int imageHeight = totalRow * rowHeight + 50;
        // 起始高度
        int startHeight = 10;
        // 起始宽度
        int startWidth = 10;
        // 单元格宽度
        int colwidth = (imageWidth - 20) / totalCol;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(new Color(220, 240, 240));

        //画横线
        for (int j = 0; j < totalRow; j++) {
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth, startHeight + (j + 1) * rowHeight,
                    startWidth + colwidth * totalCol, startHeight + (j + 1) * rowHeight);
        }
        //画竖线
        for (int k = 0; k < totalCol + 1; k++) {
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth + k * colwidth, startHeight + rowHeight,
                    startWidth + k * colwidth, startHeight + rowHeight * totalRow);
        }
        //设置字体
        Font font = new Font("微软雅黑", Font.BOLD, fontTitleSize);
        graphics.setFont(font);
        //写标题
        String title = "【指标完成进度】";
        graphics.drawString(title, startWidth, startHeight + rowHeight - 10);
        //写入内容
        for (int n = 0; n < cellsValue.length; n++) {
            for (int l = 0; l < cellsValue[n].length; l++) {
                if (n == 0) {
                    font = new Font("微软雅黑", Font.BOLD, fontTitleSize);
                    graphics.setFont(font);
                } else if (n > 0 && l > 0) {
                    font = new Font("微软雅黑", Font.PLAIN, fontTitleSize);
                    graphics.setFont(font);
                    graphics.setColor(Color.RED);
                } else {
                    font = new Font("微软雅黑", Font.PLAIN, fontTitleSize);
                    graphics.setFont(font);
                    graphics.setColor(Color.BLACK);
                }
                graphics.drawString(cellsValue[n][l], startWidth + colwidth * l + 5,
                        startHeight + rowHeight * (n + 2) - 10);
            }
        }
        // 保存图片
        createImage(image, path);
    }

    /**
     * 根据str,font的样式以及输出文件目录
     */
    public static void createImage(String str, Font font, File outFile,
                                   Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(Color.red);// 在换成黑色
        g.setFont(font);// 设置画笔字体
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString(str, i * 680, y);// 画出字符串
        }
        g.dispose();
        ImageIO.write(image, "png", outFile);// 输出png图片
    }

    /**
     * 将图片保存到指定位置
     *
     * @param image        缓冲文件类
     * @param fileLocation 文件位置
     */
    public void createImage(BufferedImage image, String fileLocation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 计算文本高度
         *
         * @param content    文本内容
         * @param font       文本字体
         * @param width      文本的行宽
         * @param lineHeight 文本行高
         */
//        public static ContentImgInfo effectiveHeight(String content, Font font, int width, int lineHeight) {
//            int imgHeight = 0;
//            ContentImgInfo contentImgInfo = new ContentImgInfo();
//            try {
//                BufferedImage image = new BufferedImage(100, 300, BufferedImage.TYPE_INT_RGB);
//                Graphics2D g = image.createGraphics();
//                g.setFont(font);
//                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
//
//                FontMetrics fm = g.getFontMetrics(font);
//
//                // 获取字体高度
//                int fontHeight = fm.getHeight() + lineHeight;
//
//                // 添加对文字的换行处理
//                char firstChar = "中文".charAt(0);
//                int charWidth = fm.charWidth(firstChar); // 字符的宽度
//                int num = (int) Math.ceil(width / charWidth);
//                int lineNum = EffHelper.ceil(content.length(), num) + 1;
//                imgHeight = lineNum * fontHeight;
//
//                g.dispose();
//
//                // 设置文本图片信息
//                contentImgInfo.setFontHeight(fontHeight);
//                contentImgInfo.setContentImgHeight(imgHeight);
//                contentImgInfo.setLineNum(lineNum);
//
//
//            } catch (Exception e) {
//
//            }
//            return contentImgInfo;
//        }
    }
}
