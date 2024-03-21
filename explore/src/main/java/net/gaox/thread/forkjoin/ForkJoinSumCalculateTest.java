package net.gaox.thread.forkjoin;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * <p> ForkJoin 累加示例 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-21 18:38
 */
@Slf4j
public class ForkJoinSumCalculateTest {

    private static final Long MAX_VALUE = 500_000_000L;

    @Test
    public void baseFor() {
        Instant start = Instant.now();

        long sum = 0L;
        for (long i = 0L; i <= MAX_VALUE; i++) {
            sum += i;
        }
        log.info("sum = " + sum);

        Instant end = Instant.now();

        log.info("耗费时间为：{}", Duration.between(start, end).toMillis());
    }

    //java8 新特性
    @Test
    public void stream() {
        Instant start = Instant.now();

        Long sum = LongStream.rangeClosed(0L, MAX_VALUE).parallel()
                .reduce(0L, Long::sum);
        log.info("sum = " + sum);

        Instant end = Instant.now();

        log.info("耗费时间为：{}", Duration.between(start, end).toMillis());
    }

    @Test
    public void forkJoin() {
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, MAX_VALUE, 1000_000L);
        Long sum = pool.invoke(task);
        log.info("sum = " + sum);

        Instant end = Instant.now();

        log.info("耗费时间为：{}", Duration.between(start, end).toMillis());

    }
}
