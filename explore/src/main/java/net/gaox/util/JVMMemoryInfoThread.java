package net.gaox.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.concurrent.TimeUnit;

/**
 * <p> JVM 内存信息输出线程 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 15:05
 */
@Slf4j
public class JVMMemoryInfoThread extends Thread {

    @SneakyThrows
    @Override
    public void run() {
        setName("jvm-memory-info");

        while (true) {
            // 获取Runtime对象
            Runtime runtime = Runtime.getRuntime();
            // 获取JVM最大可申请内存
            long maxMemory = runtime.maxMemory();
            // 获取当前JVM已分配的内存总数
            long totalMemory = runtime.totalMemory();
            // 获取JVM当前空闲内存量
            long freeMemory = runtime.freeMemory();

            log.info("JVM maxMemory: {} MB, totalMemory: {} MB, freeMemory: {} MB.",
                    bytes2mb(maxMemory), bytes2mb(totalMemory), bytes2mb(freeMemory));

            // 使用Java管理扩展（JMX）获取更详细的内存信息
            MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();

            log.info("堆内存使用情况 init: {} MB, \tused: {} MB, \tcommitted: {} MB, \tmax: {} MB.",
                    bytes2mb(heapMemoryUsage.getInit()), bytes2mb(heapMemoryUsage.getUsed()),
                    bytes2mb(heapMemoryUsage.getCommitted()), bytes2mb(heapMemoryUsage.getMax()));

            MemoryUsage nonHeapMemoryUsage = memoryMxBean.getNonHeapMemoryUsage();
            log.info("非堆内存使用情况 init: {} MB, \tused: {} MB, \tcommitted: {} MB, max: {} MB.",
                    bytes2mb(nonHeapMemoryUsage.getInit()), bytes2mb(nonHeapMemoryUsage.getUsed()),
                    bytes2mb(nonHeapMemoryUsage.getCommitted()), bytes2mb(nonHeapMemoryUsage.getMax()));
            // 输出间隙
            TimeUnit.SECONDS.sleep(2);
        }

    }

    /**
     * 将字节转换为兆字节的方法
     *
     * @param bytes bytes
     * @return MB
     */
    private static String bytes2mb(long bytes) {
        return String.format("%.2f", (double) bytes / (1024 * 1024));
    }

}
