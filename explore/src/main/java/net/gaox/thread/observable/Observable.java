package net.gaox.thread.observable;

/**
 * <p> 线程运行状态观察者接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-27 13:03
 */
public interface Observable {

    Cycle getCycle();

    void start();

    void interrupt();
}
