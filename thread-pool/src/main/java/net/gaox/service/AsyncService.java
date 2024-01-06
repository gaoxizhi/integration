package net.gaox.service;


import net.gaox.response.BaseResponse;

import java.util.concurrent.Future;

/**
 * <p> 异步处理接口 </p>
 *
 * @author gaox·Eric
 * @date 2023-07-13 23:38
 */

public interface AsyncService {

    /**
     * 任务一（有返回结果）
     *
     * @return future
     */
    Future<BaseResponse> doTaskOne();

    /**
     * 任务二（有返回结果）
     *
     * @return future
     */
    Future<BaseResponse> doTaskTwo();

    /**
     * 任务三（无返回结果）
     */
    void doTaskThree();
}
