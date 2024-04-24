package net.gaox.order.task;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * <p> 团购者 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-22 15:30
 */
@Slf4j
public class GroupBuyer implements Runnable {

    @Getter
    private int userId;
    @Getter
    private int amount;

    private CyclicBarrier barrier;

    /**
     * 共享的总购买数量
     */
    @Getter
    private static int totalAmount;

    public GroupBuyer(int userId, int amount, CyclicBarrier barrier) {
        this.userId = userId;
        this.amount = amount;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        // 模拟用户参与拼单的过程（例如商品选择、确认数量等）
        log.info("用户[{}]选择购买[{}]份，并等待其他用户一起拼单...", userId, amount);

        try {
            // 等待所有用户都准备好
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            log.error("发生异常：", e);
            return;
        }
        // 所有用户都到达栅栏屏障后，执行拼单下单操作
        placeOrder();
    }

    private void placeOrder() {
        // 模拟下单操作，如：合并订单、计算总价、发起支付等
        BigDecimal divide = new BigDecimal(amount).divide(new BigDecimal(totalAmount), 3, BigDecimal.ROUND_HALF_UP);

        String ratio = divide.toString();
        log.info("用户[{}]与其他用户一起下单，共购买[{}]份，比例[{}]", userId, amount, ratio);
    }

    public static void initTotalAmount() {
        GroupBuyer.totalAmount = 0;
    }

    public static void addTotalAmount(int amount) {
        GroupBuyer.totalAmount += amount;
    }

}
