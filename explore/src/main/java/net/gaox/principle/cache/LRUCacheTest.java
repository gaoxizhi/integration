package net.gaox.principle.cache;

import lombok.extern.slf4j.Slf4j;
import net.gaox.util.JVMMemoryInfoThread;

import java.util.concurrent.TimeUnit;

/**
 * <p> LRU 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 13:50
 */
@Slf4j
public class LRUCacheTest {
    public static void main(String[] args) throws InterruptedException {

        // 内存信息监控，每 2 秒输出一次
        JVMMemoryInfoThread memoryInfo = new JVMMemoryInfoThread();
        memoryInfo.setDaemon(true);
        memoryInfo.start();

        /// log.info("同步展示内存情况");
        // removeEldest();
        // lruCacheToOom();
        // softLruCacheCanNotOom();

    }

    /**
     * 超多缓存，内存溢出
     *
     * @throws InterruptedException 中断异常
     */
    private static void lruCacheToOom() throws InterruptedException {
        // 测试平台 MacBook Pro 16GB
        // JVM maxMemory: 3643.50 MB, totalMemory: 3643.50 MB, freeMemory: 248.79 MB.
        // 堆内存使用情况 init: 256.00 MB, 	used: 3394.72 MB, 	committed: 3643.50 MB, 	max: 3643.50 MB.
        // 非堆内存使用情况 init: 2.44 MB, 	used: 11.92 MB, 	committed: 12.50 MB,    max: 0.00 MB.
        // The 3391 reference stored at cache.
        // * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

        LRUCache<Integer, Reference> cache = new LRUCache<>(6000, key -> new Reference());
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            cache.get(i);
            TimeUnit.MILLISECONDS.sleep(30);
            log.info("The " + i + " reference stored at cache.");
        }
    }

    /**
     * 超多使用软引用的缓存，内存未溢出
     *
     * @throws InterruptedException 中断异常
     */
    private static void softLruCacheCanNotOom() throws InterruptedException {
        SoftLRUCache<Integer, Reference> cache = new SoftLRUCache<>(6000, key -> new Reference());
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            cache.get(i);
            TimeUnit.MILLISECONDS.sleep(30);
            log.info("The " + i + " reference stored at cache.");
        }
    }

    /**
     * LRU 策略，移除最长时间未使用的数据
     */
    private static void removeEldest() {
        LRUCache<String, Reference> cache = new LRUCache<>(5, key -> new Reference());
        cache.get("bill");
        cache.get("gaox");
        cache.get("annie");
        cache.get("kate");
        cache.get("lisa");
        //上面的数据在缓存中的新旧程度和添加顺序一致

        log.info("before get jenny cache: {}", cache);
        // bill 将会被踢出
        cache.get("Jenny");
        log.info("after get jenny cache: {}", cache);
        // 获取 gaox
        cache.get("gaox");
        log.info("after get gaox cache: {}", cache);
    }

}
