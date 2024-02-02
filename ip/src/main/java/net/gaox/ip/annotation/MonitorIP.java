package net.gaox.ip.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> 监视IP 注解 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 23:18
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitorIP {
}
