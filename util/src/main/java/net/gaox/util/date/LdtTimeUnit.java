package net.gaox.util.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p> LocalDateTime 时间工具类 </p>
 *
 * @author gaox·Eric
 * @date 2018/12/13 11:23
 */
public class LdtTimeUnit {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 字符串转换成LocalDateTime对象
     */
    public static LocalDateTime parseLdt(String string) {
        if (1 == string.split(" ").length) {
            string += " 00:00:00";
        }
        return LocalDateTime.parse(string, DATETIME_FORMATTER);
    }

    /**
     * 字符串转换成LocalDate对象
     */
    public static LocalDate parseLd(String string) {
        return parseLdt(string).toLocalDate();
    }

    /**
     * LocalDateTime 转换成 日期+时间 字符串
     */
    public static String ldt2ldtStr(LocalDateTime ldt) {
        return ldt.format(DATETIME_FORMATTER);
    }

    /**
     * LocalDateTime 转换成 日期 字符串
     */
    public static String ldt2ldStr(LocalDateTime ldt) {
        return ldt.format(DATE_FORMATTER);
    }

    /**
     * Date转LocalDate
     *
     * @param date date
     * @return ld
     */
    public static LocalDate date2ld(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date转LocalTime
     *
     * @param date date
     * @return lt
     */
    public static LocalTime date2lt(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date date
     * @return Ldt
     */
    public static LocalDateTime date2ldt(Date date) {
        //An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        Instant instant = date.toInstant();
        //A time-zone ID, such as {@code Europe/Paris}.(时区)
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime ldt
     * @return date
     */
    public static Date ldt2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        //Combines this date-time with a time-zone to create a  ZonedDateTime.
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 分析两时间天间隔
     */
    public static Integer getDayLead(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            return -1;
        }
        try {
            return (int) (end.toLocalDate().toEpochDay() - start.toLocalDate().toEpochDay());
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 时间差计算
     *
     * @param last 开始时间
     *             LocalDate 转 LocalDateTime
     *             LocalDateTime.of(localDate, LocalTime.MIN)
     * @param now  结束时间
     * @return 时间差字符串
     */
    public static String durationTime(LocalDateTime last, LocalDateTime now) {
        if ((last.toLocalDate().equals(now.toLocalDate())) &&
                (last.toLocalTime().toSecondOfDay() == now.toLocalTime().toSecondOfDay())) {
            return "刚刚";
        }

        StringBuilder builder = new StringBuilder();

        Duration between = Duration.between(last, now);
        if (0 == between.toDays()) {
            long hour = between.toHours();
            long minute = between.toMinutes();
            if (hour > 0) {
                builder.append(hour + "小时");
            } else if (minute > 0) {
                builder.append(minute + "分钟");
            } else {
                long seconds = between.getSeconds();
                builder.append(seconds + "秒");
            }
            return builder.append("前").toString();

        } else {
            Period period = Period.between(last.toLocalDate(), now.toLocalDate());
            int months = period.getMonths();
            int years = period.getYears();
            if (years > 0) {
                return years + "年前";
            } else if (months > 0) {
                return months + "月前";
            } else {
                return period.getDays() + "天前";
            }
        }
    }

}