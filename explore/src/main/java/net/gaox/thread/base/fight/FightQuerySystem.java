package net.gaox.thread.base.fight;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <p> 航班查询实现 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-19 18:59
 */
@Slf4j
public class FightQuerySystem {
    private static List<String> fightCompany = Arrays.asList("CSA", "CEA", "HNA");

    public static void main(String[] args) {
        List<String> results = search("SH", "BJ");
        log.info("===========result begin===========");
        results.forEach(log::info);
        log.info("===========result   end===========");

    }

    private static List<String> search(String original, String dest) {
        final List<String> result = new ArrayList<>();
        List<FightQueryTask> tasks = fightCompany.stream()
                .map(f -> new FightQueryTask(f, original, dest))
                .collect(toList());

        tasks.forEach(Thread::start);

        // join task线程，会使当前线程进入等待，那么在此期间当前线程是处于 BLOCKED
        // 直到task线程结束生命周期，或者到达给定的时间
        tasks.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        tasks.stream().map(FightQuery::get).forEach(result::addAll);
        return result;
    }

}
