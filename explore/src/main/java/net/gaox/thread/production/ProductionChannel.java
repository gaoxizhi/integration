package net.gaox.thread.production;

/**
 * <p> 产品传送带 </p>
 * 在传送带上除了负责产品加工的工人之外，还有在传送带上等待加工的产品
 *
 * @author gaox·Eric
 * @date 2024-01-30 18:12
 */
public class ProductionChannel {

    /**
     * 传送带上最多可以有多少个待加工的产品
     */
    private static final int MAX_PRODUCTION_NUM = 100;

    /**
     * 主要用来存放待加工的产品，也就是传送带
     */
    private final FanProduction[] productionQueue;

    /**
     * 半成品队列尾
     */
    private int tail;

    /**
     * 半成品队列头
     */
    private int head;

    /**
     * 当前在流水线上有多少个待加工的产品
     */
    private int total;

    /**
     * 在流水线上工作的工人
     */
    private final Worker[] workerList;

    /**
     * 流水线构造器
     *
     * @param workers 需要多少个流水线工人
     */
    public ProductionChannel(int workers) {
        this.productionQueue = new FanProduction[MAX_PRODUCTION_NUM];

        this.workerList = new Worker[workers];
        for (int i = 0; i < workers; i++) {
            // 实例化每一个工人（Worker线程）并且启动
            this.workerList[i] = new Worker("Worker-" + i + 1, this);
            this.workerList[i].start();
        }
    }

    /**
     * 接受来自上游的半成品（待加工的产品）
     *
     * @param production 半成品
     */
    public void offerProduction(FanProduction production) {
        synchronized (this) {
            // 当传送带上半成品超过了最大值时需要阻塞上游，再传送产品
            while (total >= productionQueue.length) {
                try {
                    // wait 方法会释放当前线程的锁，进入阻塞状态，等待其他线程执行 notify 被唤醒
                    this.wait();
                } catch (InterruptedException e) {
                }
            }

            // 将产品放到传送带末尾
            productionQueue[tail] = production;
            // 队尾向后移动
            tail = (tail + 1) % productionQueue.length;
            total++;
            // 通知工人线程工作
            this.notifyAll();
        }
    }

    /**
     * 从传送带上取出一个半成品
     *
     * @return 半成品
     */
    public FanProduction takeProduction() {
        synchronized (this) {
            // 当传送带上没有产品时，工人等待着产品从上游输送到传送带上
            while (total <= 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                }
            }

            // 获取产品
            FanProduction prod = productionQueue[head];
            // // 队首向前移动
            productionQueue[head] = null;
            head = (head + 1) % productionQueue.length;
            total--;
            this.notifyAll();
            return prod;
        }
    }

}