package net.gaox.javaSE2018.base.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: <p>  </p>
 * @ClassName DateFormat
 * @Author: gaox·Eric
 * @Date: 2019/3/31 19:01
 */
public class DateFormat {

    /**
     * yyyy：年 MM：月 dd：日
     * hh：1~12小时制(1-12)
     * HH：24小时制(0-23)
     * mm：分 ss：秒 S：毫秒
     * E：星期几
     * D：一年中的第几天
     * F：一月中的第几个星期(会把这个月总共过的天数除以7)
     * w：一年中的第几个星期
     * W：一月中的第几星期(会根据实际情况来算)
     * a：上下午标识
     * k：和HH差不多，表示一天24小时制(1-24)。
     * K：和hh差不多表示一天12小时制(0-11)
     * z：表示时区
     *
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        //时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yy年MM月dd日 HH时mm分ss秒\n "
                + "本年第w周 时区：z a E");
        //获取系统当前时间，并转换输出
        Date data = new Date();
        System.out.println("获得系统时间:\n" + sdf.format(data));
        //将字符串转换成时间
        String str = "2015/09/12 12:30:21";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // String->Date依赖SimpleDateFormat的parse ()方法
        Date date = sdf2.parse(str);
        System.out.println("string To Date:\n" + sdf.format(date).toString());

    }
}