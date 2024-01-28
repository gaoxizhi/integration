package net.gaox.thread.observable;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p> 观察者模式，监视线程状态 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-27 13:56
 */
@Slf4j
public class ObservableThreadTest {
    public static void main(String[] args) throws InterruptedException {
        final TaskLifecycle<String> lifecycle = new EmptyLifecycle<String>() {
            @Override
            public void onFinish(Thread thread, String result) {
                log.info("The result is {}", result);
            }
        };


        Observable observableThread = new ObservableThread<>(lifecycle, () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(" finished done.");
            return "Hello Observer";
        });

        observableThread.start();
        Cycle cycle = observableThread.getCycle();
        List<Cycle> endCycle = Arrays.asList(Cycle.DONE, Cycle.ERROR);
        while (!endCycle.contains(cycle)) {
            TimeUnit.MILLISECONDS.sleep(100);
            cycle = observableThread.getCycle();
            log.info("The cycle is {}", cycle);
        }
    }

}
