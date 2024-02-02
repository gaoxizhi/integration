package net.gaox.ip.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * <p> 获取当前 http 的 request、response 工具类 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 23:28
 */
public class HttpContextUtil {

    private HttpContextUtil() {
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

}
