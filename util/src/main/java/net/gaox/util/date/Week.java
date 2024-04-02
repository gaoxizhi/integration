package net.gaox.util.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <p> 周数 </p>
 *
 * @author gaox·Eric
 * @date 2019/9/16 18:58
 */
public class Week {

    public static LocalDate getDateByYearAndWeekNumAndDayOfWeek(Integer year, Integer num, DayOfWeek dayOfWeek) {
        //2019-W01-01获取第一周的周一日期，2019-W02-07获取第二周的周日日期
        String weekDate = String.format("%s-W%02d-%s", year, num, dayOfWeek.getValue());
        return LocalDate.parse(weekDate, DateTimeFormatter.ISO_WEEK_DATE);
    }

}