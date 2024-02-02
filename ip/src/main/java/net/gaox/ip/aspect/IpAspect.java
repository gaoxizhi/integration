package net.gaox.ip.aspect;

import lombok.extern.slf4j.Slf4j;
import net.gaox.ip.util.AddressUtil;
import net.gaox.ip.util.HttpContextUtil;
import net.gaox.ip.util.IpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * <p> IP注解监视 IP信息 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 23:18
 */
@Slf4j
@Aspect
@Component
public class IpAspect {

    @Pointcut("@annotation(net.gaox.ip.annotation.MonitorIP)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IpUtil.getIpAddress(request);
        log.info(MessageFormat.format("当前IP为:[{0}]；当前IP地址解析出来的地址为:[{1}]", ip, AddressUtil.getCityInfo(ip)));
        return point.proceed();
    }

}
