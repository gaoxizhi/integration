package net.gaox.ws.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * <p> web 监听器 </p>
 * 解决 GetHttpSessionConfigurator NPE
 *
 * @author gaox·Eric
 * @date 2020/4/1 00:44
 */
@Slf4j
@WebListener
public class RequestListener implements ServletRequestListener {
    public RequestListener() {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest servletRequest = (HttpServletRequest) sre.getServletRequest();

        log.debug("[uri]-->[{}] ", servletRequest.getRequestURI());
        log.debug("[addr]-->[{}] ", sre.getServletRequest().getRemoteAddr());
        // 将所有request请求都携带上httpSession
        // If there is no "HTTP session under which a client is operating"
        // then there is no requirement to create one.
        log.debug("[sessionId]-->[{}] ", servletRequest.getSession().getId());
    }
}