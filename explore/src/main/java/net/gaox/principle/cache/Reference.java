package net.gaox.principle.cache;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> GC 释放资源的动作 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 13:40
 */
@Slf4j
public class Reference {

    /**
     * 1M 大小的空间
     */
    private final byte[] data = new byte[2 << 19];

    /**
     * 释放资源的动作
     * 当 Reference 对象被垃圾回收时，会执行该方法
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        log.info("the reference will be GC.");
    }

}
