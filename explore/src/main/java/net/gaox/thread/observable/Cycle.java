package net.gaox.thread.observable;

/**
 * <p> 任务状态枚举 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-27 13:01
 */
public enum Cycle {

    /**
     * 开始
     */
    STARTED,

    /**
     * 运行中
     */
    RUNNING,

    /**
     * 运行成功
     */
    DONE,

    /**
     * 运行失败
     */
    ERROR,
    ;

}
