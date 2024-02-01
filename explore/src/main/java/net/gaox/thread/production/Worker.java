package net.gaox.thread.production;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p> 工人线程 </p>
 * 从流水线上取产品，加工，再放回流水线
 *
 * @author gaox·Eric
 * @date 2024-01-30 18:25
 */
@Slf4j
public class Worker extends Thread {

    /**
     * 流水线
     */
    private final ProductionChannel channel;

    /**
     * 模拟工人不同的加工时间
     */
    private final int PROCESSING_SPEED = ThreadLocalRandom.current().nextInt(120);

    public Worker(String workerName, ProductionChannel channel) {
        super(workerName);
        this.channel = channel;
        log.info("Worker {}, processing speed = {} ms.", workerName, PROCESSING_SPEED);
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 从流水线上取出一个产品
                FanProduction fan = channel.takeProduction();
                log.info(" process the {}", fan);
                // 对产品进行加工
                fan.create();
                TimeUnit.MILLISECONDS.sleep(PROCESSING_SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}