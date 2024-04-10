package net.gaox.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.gaox.holder.RequestHolder;
import net.gaox.response.BaseResponse;
import net.gaox.service.AsyncService;
import net.gaox.util.HttpUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;

/**
 * <p> 异步处理实现类 </p>
 *
 * @author gaox·Eric
 * @date 2023-07-22 00:13
 */

@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async
    public Future<BaseResponse> doTaskOne() {
        log.info("开始做任务一（睡眠2s）");
        HttpServletRequest request = RequestHolder.getRequest();
        methodThread();
        log.info("任务一完成，当前线程为 {}，请求方法为 {}，请求路径为：{}", Thread.currentThread().getName(),
                request.getMethod(), request.getRequestURL().toString());
        return new AsyncResult<>(BaseResponse.ok("任务一完成"));
    }

    @Override
    @Async("log")
    public Future<BaseResponse> doTaskTwo() {
        log.info("开始做任务二（睡眠2s）");
        methodThread();
        HttpServletRequest request = RequestHolder.getRequest();
        log.info("任务二完成，当前线程为 {}，请求方法为 {}，请求路径为：{}", Thread.currentThread().getName(),
                request.getMethod(), request.getRequestURL().toString());
        return new AsyncResult<>(BaseResponse.ok("任务二完成"));
    }

    @Override
    @Async
    public void doTaskThree() {
        log.info("开始做任务三（睡眠5s）");
        HttpServletRequest request = RequestHolder.getRequest();
        HttpUtil.lookHttpScopeAttribute();

        // 提前获取到，执行 sleep 后，request 内容被清空了
        String name = Thread.currentThread().getName();
        String method = request.getMethod();
        String url = request.getRequestURL().toString();

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            Thread.interrupted();
            log.error("sleep异常：", e);
        }

        log.info("任务三完成，当前线程为 {}，请求方法为 {}，请求路径为：{}", name, method, url);
    }


    /**
     * 接口睡眠
     */
    private void methodThread() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            Thread.interrupted();
            log.error("sleep异常：", e);
        }
    }
}

