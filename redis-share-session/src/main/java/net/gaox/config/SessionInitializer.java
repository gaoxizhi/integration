package net.gaox.config;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * <p> 初始化Session配置 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 23:06
 */

public class SessionInitializer extends AbstractHttpSessionApplicationInitializer {
    public SessionInitializer() {
        super(SessionConfig.class);
    }
}
