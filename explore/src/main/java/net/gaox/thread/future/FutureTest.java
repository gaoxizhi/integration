package net.gaox.thread.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p> Future 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 10:02
 */
@Slf4j
public class FutureTest {

    public static void main(String[] args) throws InterruptedException {
        // 定义有返回值的 FutureService
        FutureService<String, Integer> service = FutureService.newService();

        // 参数：任务，回调方法
        service.submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return input.length();
        }, "Hello", FutureTest::logInfo);
    }

    /**
     * 回调静态方法
     *
     * @param o 任务结果
     */
    private static void logInfo(Object o) {
        log.info("result = {}", o.toString());
    }

}
