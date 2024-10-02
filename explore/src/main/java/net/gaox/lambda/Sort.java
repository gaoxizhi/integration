package net.gaox.lambda;

import lombok.extern.slf4j.Slf4j;
import net.gaox.entity.ManageSort;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * <p> 流排序 </p>
 *
 * @author gaox·Eric
 * @date 2024-07-09 22:00
 */
@Slf4j
public class Sort {

    public static void main(String[] args) {
        List<String> strings = asList("asd", "tgh", "oks", "lkc", "qae");
        //排序 匿名内部方式
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        //排序 使用String中的compareTo方法
        Collections.sort(strings, (o1, o2) -> o1.compareTo(o2));
        Collections.sort(strings, String::compareTo);
        strings.sort(String::compareTo);


        //最终列表
        ArrayList<ManageSort> list = new ArrayList<>();
        final LocalDate beginTime = LocalDate.of(2019, 5, 1);
        final LocalDate toDay = LocalDate.now();

        Random random2 = new Random(1000000);
        for (int i = 0; i <= beginTime.until(toDay, ChronoUnit.DAYS); i++) {
            long nextLong = random2.nextLong();
            int typeId = (int) (nextLong / (i + 1));
            ManageSort manageSort = new ManageSort().setTime(beginTime.plusDays(i)).setType("type" + typeId)
                    .setDateTime(new Date(System.currentTimeMillis() + nextLong));
            list.add(manageSort);
        }

        //自定义排序规则：包含大小相等条件，日期（后-->前）
        list.sort((o1, o2) -> o1.getTime().isBefore(o2.getTime()) ? 1 : (o1.getTime().isAfter(o2.getTime()) ? -1 : 0));

        // 时间1排序
        Comparator<ManageSort> dateTimeComparator = (o1, o2) -> (int) (o1.getDateTime().getTime() - o2.getDateTime().getTime());
        // 时间2排序
        Comparator<ManageSort> dateComparator = Comparator.comparing(ManageSort::getTime);
        // type排序
        Comparator<ManageSort> typeComparator = Comparator.comparing(ManageSort::getType);
        // 时间1排序 逆序，时间2排序，type排序
        list.sort(dateTimeComparator.reversed().thenComparing(dateComparator).thenComparing(typeComparator));

        list.sort((o1, o2) -> {
            int compare1 = o2.getTime().isBefore(o1.getTime()) ? 1 : (o1.getTime().isAfter(o2.getTime()) ? -1 : 0);
            int compare2 = o1.getTime().compareTo(o2.getTime());
            int compare3 = o1.getType().compareTo(o2.getType());
            return -compare1 == 0 ? (compare2 == 0 ? compare3 : compare2) : -compare1;
        });
    }

}
