package net.gaox.excel.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;


/**
 * <p> Excel 耗时记录 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@Slf4j
@Aspect
@Component
public class ExcelDurationAspect {

    /**
     * 切入方式 方法名
     *
     * @param joinPoint 切点
     * @return 导出耗时记录
     */
    @Around("execution(public void net.gaox.excel.controller.BigTableController.exportExcel*(..))")
    public void exportExcel(ProceedingJoinPoint joinPoint) {
        long startTime = System.nanoTime();
        log.info("开始导出：" + joinPoint.getSignature().getName());
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            Duration time = Duration.ofNanos(System.nanoTime() - startTime);
            log.info("导出结束，消耗了：" + time.getSeconds() + "s");
        }

    }

    /**
     * 导入耗时
     *
     * @param joinPoint 切点
     */
    @Around("execution(public void net.gaox.excel.controller.BigTableController.importExcel*(..))")
    public void importExcel(ProceedingJoinPoint joinPoint) {
        long startTime = System.nanoTime();
        log.info("开始导入：" + joinPoint.getSignature().getName());
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            Duration time = Duration.ofNanos(System.nanoTime() - startTime);
            log.info("导入结束，消耗了：" + time.getSeconds() + "s");
        }
    }

}
