package net.gaox.system.feign.fallback;

import com.netflix.client.ClientException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import net.gaox.domain.entity.SysKv;
import net.gaox.domain.model.dto.KvQuery;
import net.gaox.system.feign.KvFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p> 默认 fallback factory </p>
 *
 * @author gaox·Eric
 * @date 2024-04-13 13:32
 */
@Slf4j
@Component
public class KvFeignClientFallbackFactory implements FallbackFactory<KvFeignClient> {
    @Override
    public KvFeignClient create(Throwable cause) {
        return new KvFeignClient() {
            @Override
            public String get(String key) {
                throw new RuntimeException(handleException(cause));
            }

            @Override
            public List<SysKv> like(String key) {
                throw new RuntimeException(handleException(cause));
            }

            @Override
            public List<SysKv> list(KvQuery kvQuery) {
                throw new RuntimeException(handleException(cause));
            }

            @Override
            public String echo(String string) {
                throw new RuntimeException(handleException(cause));
            }

            private String handleException(Throwable cause) {
                // 当服务不可用时执行的fallback逻辑
                if (cause instanceof RuntimeException &&
                        cause.getCause() instanceof ClientException) {
                    // 根据实际情况处理异常，例如记录日志、返回默认值或错误信息
                    log.error("服务调用失败，原因：{}", cause.getMessage());
                    return getDefaultResponse();
                } else {
                    throw new RuntimeException("未知异常", cause);
                }
            }

            private String getDefaultResponse() {
                // 返回一个默认响应，例如空值、错误消息或者一个假数据模型
                return "Service Unavailable, fallback response.";
            }
        };
    }

}
