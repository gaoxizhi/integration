package net.gaox.lambda.functionalInterface;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.function.BinaryOperator;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2021/6/11 21:41
 */
public class FunctionTest {
    public static void main(String[] args) {
        // 为了将 Lambda 表达式作为参数传递，接收 Lambda 表达式的参数类型必须是与该 Lambda 表达式兼容的函数式接口的类型
        String newStr = toUpperString((str) -> str.toUpperCase(), "gaoxizhi");
        System.out.println(newStr);

        Arrays.asList("gaox", "gaoxizhi").stream().forEach((x) -> System.out.println(x));
        Arrays.asList("gaox", "gaoxizhi").stream().forEach(System.out::println);

        BinaryOperator<Double> dbo = (x, y) -> Math.pow(x, y);
        BinaryOperator<Double> bo = Math::pow;

        LocalDate nextSunday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

    }

    /**
     * 字符串转大写
     *
     * @param func 函数式接口
     * @param str  参数字符串
     * @return 大写字符串
     */
    private static String toUpperString(MyFunc<String> func, String str) {
        return func.getValue(str);
    }
}




























