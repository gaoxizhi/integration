package net.gaox.thread.latch.test;

import lombok.extern.slf4j.Slf4j;
import net.gaox.thread.latch.Latch;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p> 旅游等待汇集后出发 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 13:52
 */
@Slf4j
public class Traveling extends Thread {

    /**
     * 门阀
     */
    private final Latch latch;

    /**
     * 人名
     */
    private final String userName;

    /**
     * 交通工具
     */
    private final String transportation;

    /**
     * 构造函数
     *
     * @param latch          门阀
     * @param userName       人名
     * @param transportation 交通工具
     */
    public Traveling(Latch latch, String userName, String transportation) {
        this.latch = latch;
        this.userName = userName;
        this.transportation = transportation;
    }

    /**
     * 运行函数
     */
    @Override
    public void run() {
        log.info("{} start take the transportation [{}].", userName, transportation);
        // 人乘坐交通工具花费在路上的时间（使用随机数字模拟）
        int timeConsuming = ThreadLocalRandom.current().nextInt(10);
        try {
            TimeUnit.SECONDS.sleep(timeConsuming);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{} arrived by {} use time {} SECONDS.", userName, transportation, timeConsuming);
        // 完成任务时使计数器减一
        latch.countDown();
    }

}
