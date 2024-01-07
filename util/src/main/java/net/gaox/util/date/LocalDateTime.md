# Java 8日期时间API

|类| 说明     |
|:---|:-------|
|Instant| 瞬时实例   |
|ZoneId| 时区     |
|ZoneOffSet| 时区毫秒数偏移 |
|LocalDate| 本地日期，不包含具体时间 例如：2014-01-14 |
|LocalTime| 本地时间，不包含日期 |
|LocalDateTime| 组合了日期和时间，但不包含时差和时区信息 |
|ZonedDateTime| 最完整的日期时间，包含时区和相对UTC或格林威治的时差 |
|DateTimeFormatter| 解析、格式化 |

## 说明
1. Java 8 的所有日期和时间 API 都是不可变类并且线程安全， 区别于 Date、Calendar 和 SimpleDateFormat 是非线程安全的。
2. java.time 包含了表示日期、时间、时间间隔的类
3. java.time.format 用于格式化
4. java.time.temporal 用于更底层的操作

## 常用工具类
[LdtTimeUnit](LdtTimeUnit.java)