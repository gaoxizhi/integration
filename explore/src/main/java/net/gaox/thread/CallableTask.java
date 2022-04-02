package net.gaox.thread;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Callable;

/**
 * <p> 线程任务 </p>
 *
 * @author gaox·Eric
 * @date 2022/4/2 22:51
 */
@Data
@NoArgsConstructor
public class CallableTask implements Callable {

    private String index;

    CallableTask(String index) {
        this.index = index;
    }

    @Override
    public Object call() throws Exception {
        Thread.sleep(2_000);
        return String.format("任务[%s]已完成！", index);
    }
}
