package net.gaox.reference;

import lombok.extern.slf4j.Slf4j;
import net.gaox.principle.cache.Reference;
import net.gaox.util.thread.AutoGcThread;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * <p> 引用探究 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 15:31
 */
@Slf4j
public class ReferenceTest {
    public static void main(String[] args) throws InterruptedException {
        // weakReferenceAfterGcBeNull();
        // weakReferenceAfterGcGetByQueue();
        phantomReferenceAfterGcGetByQueue();
    }


    /**
     * 弱引用，GC 之后，引用对象被回收
     * 无论是 young GC 还是 full GC，Weak Reference 的引用都会被垃圾回收器回收
     * Weak Reference（弱引用）可以用来做Cache，但是一般很少使用
     *
     * @throws InterruptedException 中断异常
     */
    private static void weakReferenceAfterGcBeNull() throws InterruptedException {
        Reference ref = new Reference();
        WeakReference<Reference> reference = new WeakReference<>(ref);
        log.info("reference = {}", reference.get());
        // ref = null;
        //执行GC操作
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        log.info("after GC reference = {}", reference.get());
    }

    /**
     * 从 ReferenceQueue 中获取 被垃圾回收的引用对象
     *
     * @throws InterruptedException 中断异常
     */
    private static void weakReferenceAfterGcGetByQueue() throws InterruptedException {
        //被垃圾回收的 Reference 会被加入与之关联的 ReferenceQueue 中
        ReferenceQueue<Reference> queue = new ReferenceQueue<>();
        Reference data = new Reference();
        //定义 WeakReference 并且指定关联的 Queue
        WeakReference<Reference> reference = new WeakReference<>(data, queue);
        log.info("reference = {}", reference.get());

        data = null;
        TimeUnit.SECONDS.sleep(1);
        log.info("after moment reference = {}", reference.get());

        //手动执行 gc 操作
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        log.info("after GC reference = {}", reference.get());

        // remove 方法是阻塞方法，当 data 为 null，执行 GC 后，才会返回
        java.lang.ref.Reference<? extends Reference> afterGcRef = queue.remove();
        log.info("after queue remove afterGcRef = {}", afterGcRef);
        // // 被垃圾回收之后，会从队列中获得
        // Optional<? extends Reference> optionalReference =
        //         Optional.ofNullable(afterGcRef).map(java.lang.ref.Reference::get);
        // log.info("after queue remove reference = {}", optionalReference.orElse(null));
    }

    /**
     * 从 ReferenceQueue 中获取 被垃圾回收的引用对象
     *
     * @throws InterruptedException 中断异常
     */
    private static void phantomReferenceAfterGcGetByQueue() throws InterruptedException {

        // 自动 GC 线程
        new AutoGcThread().start();

        //被垃圾回收的 Reference 会被加入与之关联的 ReferenceQueue 中
        ReferenceQueue<Reference> queue = new ReferenceQueue<>();
        Reference data = new Reference();
        //定义 WeakReference 并且指定关联的 Queue
        PhantomReference<Reference> reference = new PhantomReference<>(data, queue);
        //始终返回 null
        log.info("reference = {}", reference.get());

        data = null;
        log.info("after GC reference = {}", reference.get());

        reference.clear();
        // remove 方法是阻塞方法
        java.lang.ref.Reference<? extends Reference> afterGcRef = queue.remove();
        log.info("after queue remove afterGcRef = {}", afterGcRef);

    }

}
