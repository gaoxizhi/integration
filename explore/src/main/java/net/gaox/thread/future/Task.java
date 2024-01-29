package net.gaox.thread.future;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 09:19
 */
public interface Task<IN, OUT> {

    /**
     * 给定一个参数，经过计算返回结果
     *
     * @param input 入参
     * @return 返回结果
     */
    OUT get(IN input);

}
