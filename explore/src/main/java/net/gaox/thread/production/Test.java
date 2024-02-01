package net.gaox.thread.production;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


/**
 * <p> 流水线测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 18:52
 */
public class Test {
    public static void main(String[] args) {

        // 流水线上有5个工人
        final ProductionChannel channel = new ProductionChannel(5);
        AtomicInteger productionNo = new AtomicInteger();
        // 流水线上有8个工作人员往流水线上不停的放等待加工的半成品
        IntStream.range(1, 8).forEach(i -> new Thread(() -> {
                    while (true) {
                        channel.offerProduction(new FanProduction(productionNo.getAndIncrement()));
                        try {
                            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start()
        );
    }

}
