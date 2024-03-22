package net.gaox.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * <p> 全局过滤器，设置traceId，排在最前 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-22 23:27
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // MDC容器增加traceId
        UUID traceId = UUID.randomUUID();
        String uid = String.valueOf(traceId).replaceAll("-", "").substring(0, 16);
        MDC.put("traceId", uid);
        log.debug("pre request traceId = [{}]", uid);

        // 继续执行过滤器链
        chain.doFilter(request, response);
        MDC.remove("traceId");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
