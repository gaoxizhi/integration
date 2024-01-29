package net.gaox.thread.future;

/**
 * <p> Future设计模式 </p>
 * 提供了一种凭据式的解决方案
 *
 * @author gaox·Eric
 * @date 2024-01-29 09:06
 */
public interface Future<T> {

    /**
     * 返回计算后的结果，该方法会陷入阻塞状态
     *
     * @return 计算后的结果
     * @throws InterruptedException e
     */
    T get() throws InterruptedException;

    /**
     * 判断任务是否已经被执行完成
     *
     * @return true表示任务已经执行完成，false表示任务还没有执行完成
     */
    boolean done();

}
