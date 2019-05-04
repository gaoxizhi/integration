package net.gaox.shirojwt.controller;

import net.gaox.shirojwt.util.api.ApiResponse;
import net.gaox.shirojwt.util.exception.UnauthorizedException;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: <p>  </p>
 * @ClassName ExceptionController
 * @Author: gaox·Eric
 * @Date: 2019/5/4 01:21
 */
@RestControllerAdvice
public class ExceptionController {


    /**
     * 捕捉shiro的异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ApiResponse handle401(ShiroException e) {
        return ApiResponse.fail().error(e.getMessage());
    }

    /**
     * 捕捉UnauthorizedException
     *
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ApiResponse handle401() {
        return ApiResponse.fail().and("code", 401).error("Unauthorized");

    }

    /**
     * 捕捉其他所有异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse globalException(HttpServletRequest request, Throwable ex) {
        return ApiResponse.fail().and("code", getStatus(request).value()).error(ex.getMessage());
    }

    /**
     * 获取HTTP状态码
     *
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
