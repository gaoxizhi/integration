package net.gaox.util.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p> 时间工具类 </p>
 *
 * @author gaox·Eric
 * @date 2019/4/20 14:12
 */
public class DateUtil {

    /**
     * 返回一定时间后的日期
     *
     * @param date   开始计时的时间
     * @param year   增加的年
     * @param month  增加的月
     * @param day    增加的日
     * @param hour   增加的小时
     * @param minute 增加的分钟
     * @param second 增加的秒
     * @return 最终结果时间
     */
    public Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }

    /**
     * 返回一定时间后的日期
     *
     * @param date   开始计时的时间
     * @param year   增加的年
     * @param month  增加的月
     * @param day    增加的日
     * @param hour   增加的小时
     * @param minute 增加的分钟
     * @return 最终结果时间
     */
    public Date getAfterDate(Date date, int year, int month, int day, int hour, int minute) {
        return getAfterDate(date, year, month, day, hour, minute, 0);
    }

    /**
     * 返回一定时间后的日期
     *
     * @param date  开始计时的时间
     * @param year  增加的年
     * @param month 增加的月
     * @param day   增加的日
     * @param hour  增加的小时
     * @return 最终结果时间
     */
    public Date getAfterDate(Date date, int year, int month, int day, int hour) {
        return getAfterDate(date, year, month, day, hour, 0, 0);
    }

    /**
     * 返回一定时间后的日期
     *
     * @param date  开始计时的时间
     * @param year  增加的年
     * @param month 增加的月
     * @param day   增加的日
     * @return 最终结果时间
     */
    public Date getAfterDate(Date date, int year, int month, int day) {
        return getAfterDate(date, year, month, day, 0, 0, 0);
    }

    /**
     * 返回一定时间后的日期
     *
     * @param date  开始计时的时间
     * @param year  增加的年
     * @param month 增加的月
     * @return 最终结果时间
     */
    public Date getAfterDate(Date date, int year, int month) {
        return getAfterDate(date, year, month, 0, 0, 0, 0);
    }

    /**
     * 返回一定时间后的日期
     *
     * @param date 开始计时的时间
     * @param year 增加的年
     * @return 最终结果时间
     */
    public Date getAfterDate(Date date, int year) {
        return getAfterDate(date, year, 0, 0, 0, 0, 0);
    }
}