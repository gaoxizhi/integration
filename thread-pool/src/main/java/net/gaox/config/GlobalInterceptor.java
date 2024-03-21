package net.gaox.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * <p> 全局请求拦截器 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-21 12:51
 */
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // MDC容器增加traceId
        UUID traceId = UUID.randomUUID();
        String uid = String.valueOf(traceId).replaceAll("-", "").substring(0, 16);
        MDC.put("traceId", uid);
        log.debug("pre request traceId = [{}]", traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String traceId = MDC.get("traceId");
        MDC.remove("traceId");
        log.debug("after request traceId = [{}]", traceId);
    }

}
