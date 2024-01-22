package net.gaox.thread.base.fight;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p> 航班查询线程任务 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-19 18:51
 */
@Slf4j
public class FightQueryTask extends Thread implements FightQuery {

    /**
     * 航空公司
     */
    private final String airline;
    /**
     * 出发地
     */
    private final String origin;

    /**
     * 目的地
     */
    private final String destination;

    /**
     * 航班列表
     */
    private final List<String> flightList = new ArrayList<>();

    public FightQueryTask(String airline, String origin, String destination) {
        super(airline + "_airline");
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void run() {
        log.info("{}-query from {} to {}", airline, origin, destination);
        int randomVal = ThreadLocalRandom.current().nextInt(10);
        try {
            TimeUnit.SECONDS.sleep(randomVal);
            this.flightList.add(String.format("airline %s [%s(origin)] to [%s(destination)] consume time [%2ds]",
                    airline, origin, destination, randomVal));
            log.info("The Fight: {} list query successful", getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> get() {
        return this.flightList;
    }

}
