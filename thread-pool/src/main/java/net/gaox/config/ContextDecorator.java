package net.gaox.config;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <p> 任务装饰器 </p>
 *
 * @author gaox·Eric
 * @date 2023-07-13 23:37
 */
public class ContextDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        RequestAttributes context = RequestContextHolder.currentRequestAttributes();
        String traceId = MDC.get("traceId");
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(context);
                // 从主线程复制traceId到子线程
                MDC.put("traceId", traceId);
                runnable.run();
            } finally {
                RequestContextHolder.resetRequestAttributes();
                // 清理MDC中的traceId
                MDC.remove("traceId");
            }
        };
    }

}
