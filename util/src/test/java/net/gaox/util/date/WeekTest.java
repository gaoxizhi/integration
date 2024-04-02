package net.gaox.util.date;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * <p> 周数测试 </p>
 *
 * @author gaox·Eric
 * @date 2019/9/16 18:58
 */
public class WeekTest {

    @Test
    public void test() {
        //初始化，第一周至少1天
        WeekFields wfs = WeekFields.of(DayOfWeek.MONDAY, 1);
        //一年最后一天日期的LocalDate，如果该天获得的周数为1或52，那么该年就只有52周，否则就是53周
        //获取指定时间所在年的周数
        int num = LocalDate.of(2019, 12, 31).get(wfs.weekOfWeekBasedYear());
        // num = num == 1 ? 52 : num;
        System.out.println("第" + num + "周，周一日期:" + Week.getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.MONDAY));
        System.out.println("第" + num + "周，周二日期:" + Week.getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.TUESDAY));
        System.out.println("第" + num + "周，周三日期:" + Week.getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.WEDNESDAY));
        System.out.println("第" + num + "周，周四日期:" + Week.getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.THURSDAY));
        System.out.println("第" + num + "周，周五日期:" + Week.getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.FRIDAY));
        System.out.println("第" + num + "周，周六日期:" + Week.getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.SATURDAY));
        System.out.println("第" + num + "周，周日日期:" + Week.getDateByYearAndWeekNumAndDayOfWeek(2019, num, DayOfWeek.SUNDAY));
        //该格式周日为一周第一天，周六为一周最后一天
        System.out.println(LocalDate.parse("2019-52-1", DateTimeFormatter.ofPattern("YYYY-ww-e", Locale.CHINA)));

        //2018年第一天
        System.out.println(LocalDateTime.of(2018, 1, 1, 0, 0, 0).get(wfs.weekOfYear()));
        //2018年最后一天
        System.out.println(LocalDateTime.of(2018, 12, 31, 0, 0, 0).get(wfs.weekOfYear()));
        //2019年第一天
        System.out.println(LocalDateTime.of(2019, 1, 1, 0, 0, 0).get(wfs.weekOfYear()));
        System.out.println(LocalDateTime.of(2019, 1, 7, 0, 0, 0).get(wfs.weekOfYear()));
        System.out.println();
        //2019年最后一天
        System.out.println(LocalDateTime.of(2019, 12, 31, 0, 0, 0).get(wfs.weekOfYear()));
        //这天是星期六
        System.out.println(LocalDateTime.of(2019, 4, 6, 0, 0, 0).get(wfs.weekOfYear()));
        //这天是星期日
        System.out.println(LocalDateTime.of(2019, 4, 7, 0, 0, 0).get(wfs.weekOfYear()));
        //这天是星期一
        System.out.println(LocalDateTime.of(2019, 4, 8, 0, 0, 0).get(wfs.weekOfYear()));

        LocalDateTime staticLocalDateTime = LocalDateTime.of(2019, 9, 15, 0, 0, 0);
        System.out.println();
        System.out.println(BigDecimal.valueOf(Duration.between(LocalDateTime.of(2019, 9, 16, 0, 0, 0), LocalDateTime.of(2019, 9, 17, 0, 0, 0)).toDays() + 1).divide(BigDecimal.valueOf(7), 0, RoundingMode.UP).longValue());

        System.out.println(Duration.between(LocalDateTime.of(2019, 9, 16, 0, 0, 0), LocalDateTime.of(2019, 9, 17, 0, 0, 0)).toDays() + 1);
        System.out.println(BigDecimal.valueOf(Duration.between(LocalDateTime.of(2019, 9, 16, 0, 0, 0), LocalDateTime.of(2019, 9, 17, 0, 0, 0)).toDays() + 1).divide(BigDecimal.valueOf(7), 0, RoundingMode.UP).longValue());
        System.out.println(BigDecimal.valueOf(Duration.between(LocalDateTime.of(2019, 9, 16, 0, 0, 0), LocalDateTime.of(2019, 9, 20, 0, 0, 0)).toDays() + 1).divide(BigDecimal.valueOf(7), 0, RoundingMode.UP).longValue());
        LocalDate firstDayOfThisYear = LocalDate.of(Year.now().getValue(), 1, 1);
        LocalDateTime localDateTime = LocalDateTime.now().withMonth(1).withDayOfMonth(1);
        System.out.println(localDateTime);
        System.out.println();
        System.out.println(Duration.between(localDateTime, LocalDateTime.now()).toDays() + 1);
        //第一个周一

        System.out.println(BigDecimal.valueOf(Duration.between(localDateTime, LocalDateTime.now().withMonth(1).withDayOfMonth(7)).toDays() + 1).divide(BigDecimal.valueOf(7), 0, RoundingMode.UP).longValue());
        System.out.println(BigDecimal.valueOf(Duration.between(localDateTime, LocalDateTime.now().withMonth(12).withDayOfMonth(31)).toDays() + 1).divide(BigDecimal.valueOf(7), 0, RoundingMode.UP).longValue());

    }
}