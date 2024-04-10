package net.gaox.util;

import lombok.extern.slf4j.Slf4j;
import net.gaox.holder.RequestHolder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p> Http请求工具 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-08 12:51
 */
@Slf4j
public class HttpUtil {
    public static void lookHttpScopeAttribute() {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        StackTraceElement caller = stackTraceElements[1]; // 通常栈顶第一个是当前方法，第二个才是调用者

        String className = shortenClassName(caller.getClassName());
        String methodName = caller.getMethodName();
        int lineNumber = caller.getLineNumber();

        String name = Thread.currentThread().getName();
        HttpServletRequest request = RequestHolder.getRequest();

        Object attribute = request.getAttribute("thread");
        request.setAttribute("thread", name);
        log.warn("{}.{} at line {}: request attribute thread = {}, put new = {}",
                className, methodName, lineNumber, attribute, name);

        HttpSession session = request.getSession();
        if (null != session) {
            attribute = session.getAttribute("thread");
            session.setAttribute("thread", name);
            log.warn("{}.{} at line {}: session attribute thread = {}, put new = {}",
                    className, methodName, lineNumber, attribute, name);

            ServletContext servletContext = session.getServletContext();
            if (null != servletContext) {
                attribute = servletContext.getAttribute("thread");
                servletContext.setAttribute("thread", name);
                log.warn("{}.{} at line {}: servlet attribute thread = {}, put new = {}",
                        className, methodName, lineNumber, attribute, name);
            }
        }
    }

    /**
     * 缩短类名
     *
     * @param fullClassName 完整类名
     * @return 缩短后的类名
     */
    public static String shortenClassName(String fullClassName) {
        String[] parts = fullClassName.split("\\.");
        StringBuilder shortenedClassName = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            shortenedClassName.append(parts[i].charAt(0)).append('.');
        }

        // 拼接类名，隐藏匿名类信息
        String part = parts[parts.length - 1];
        int indexOfAnonymitySign = part.indexOf("$");
        if (0 < indexOfAnonymitySign) {
            part = part.substring(0, indexOfAnonymitySign);
        }
        shortenedClassName.append(part);

        return shortenedClassName.toString();
    }

}
