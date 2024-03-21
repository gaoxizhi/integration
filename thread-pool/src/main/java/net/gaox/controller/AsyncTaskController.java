package net.gaox.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.response.BaseResponse;
import net.gaox.service.AsyncService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * <p> 异步任务方法接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-06 13:53
 */

@Slf4j
@RestController
@RequestMapping("/async")
@RequiredArgsConstructor
@Api(tags = "async", description = "异步任务控制器")
public class AsyncTaskController {

    public final AsyncService asyncService;

    /**
     * 直接使用异步线程池
     */
    @Qualifier("asyncExecutor")
    public final Executor asyncExecutor;

    @GetMapping(value = "/task")
    @ApiOperation(value = "异步任务控制器", notes = "异步任务控制器")
    public BaseResponse<String> taskExecute() {
        long startTime = System.currentTimeMillis();
        try {
            Future<BaseResponse> r1 = asyncService.doTaskOne();
            Future<BaseResponse> r2 = asyncService.doTaskTwo();
            asyncService.doTaskThree();
            // 异步线程池执行
            asyncExecutor.execute(() -> log.info("^o^============异步线程池执行...."));
            while (true) {
                if (r1.isDone() && r2.isDone()) {
                    log.info("异步任务一、二已完成");
                    break;
                }
            }
            BaseResponse baseResponse1 = r1.get();
            BaseResponse baseResponse2 = r2.get();
            log.info("返回结果：{}，{}", baseResponse1, baseResponse2);
        } catch (Exception e) {
            log.error("执行异步任务异常 {}", e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        log.info("异步任务总耗时:{}", endTime - startTime);
        return BaseResponse.ok("异步任务全部执行成功");
    }

}
