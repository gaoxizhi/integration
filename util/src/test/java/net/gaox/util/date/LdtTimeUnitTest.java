package net.gaox.util.date;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

/**
 * <p> LdtTimeUnitTest </p>
 *
 * @author gaox·Eric
 * @date 2018/12/13 11:23
 */
public class LdtTimeUnitTest {

    @Test
    public void test() {
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println("getMonthStartTime: " + LocalDateTime.of(LdtTimeUnit.parseLdt("2015-10-21 05:21:32")
                .with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN));
        System.out.println("getMonthEndTime: " + LocalDateTime.of(LocalDateTime.now()
                .with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN));
        System.out.println("getWeekStartTime: " + LocalDateTime.of(LocalDateTime.now()
                .with(TemporalAdjusters.dayOfWeekInMonth(LocalDateTime.now()
                        .get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfMonth()) - 1, DayOfWeek.MONDAY)).toLocalDate(), LocalTime.MIN));
        System.out.println("getDayStartTime: " + LocalDateTime.of(LdtTimeUnit.parseLdt("2015-10-21").toLocalDate(), LocalTime.MIN));
        System.out.println("getDayEndTime: " + LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MAX));
        System.out.println("LDT2LDstr" + LdtTimeUnit.ldt2ldStr(LdtTimeUnit.parseLdt("2015-01-01")));

        System.out.println(LdtTimeUnit.getDayLead(LdtTimeUnit.parseLdt("2015-02-21"), LdtTimeUnit.parseLdt("2016-02-20")));
        System.out.println(LdtTimeUnit.getDayLead(LdtTimeUnit.parseLdt("2016-02-21"), LdtTimeUnit.parseLdt("2015-02-20")));
        System.out.println(LdtTimeUnit.getDayLead(LdtTimeUnit.parseLdt("2015-02-21"), LdtTimeUnit.parseLdt("2015-03-20")));
        System.out.println(LdtTimeUnit.getDayLead(LdtTimeUnit.parseLdt("2015-02-18"), LdtTimeUnit.parseLdt("2016-02-20")));
        System.out.println(LdtTimeUnit.getDayLead(LdtTimeUnit.parseLdt("2015-02-21"), LdtTimeUnit.parseLdt("2016-02-22")));
        System.out.println(LdtTimeUnit.getDayLead(LdtTimeUnit.parseLdt("2015-02-20"), LdtTimeUnit.parseLdt("2016-02-20")));
        System.out.println(LdtTimeUnit.getDayLead(LdtTimeUnit.parseLdt("2015-02-21"), LdtTimeUnit.parseLdt("2016-02-21")));

        LocalDateTime last = LocalDateTime.now();
        LocalDateTime now = LocalDateTime.now().plusNanos(3777).plusDays(1125);
        System.out.println(LdtTimeUnit.durationTime(last, now));
        // 获得该月20号
        System.out.println(last.withDayOfMonth(20));
    }

}