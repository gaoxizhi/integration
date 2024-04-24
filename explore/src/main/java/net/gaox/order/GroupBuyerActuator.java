package net.gaox.order;

import lombok.extern.slf4j.Slf4j;
import net.gaox.order.task.GroupBuyer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p> 团购执行器 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-22 15:30
 */
@Slf4j
public class GroupBuyerActuator {

    public static void main(String[] args) {
        // 假设共有5位用户参与拼单
        int userCount = 5;
        CyclicBarrier barrier = new CyclicBarrier(userCount, () -> {
            // 所有用户都到达栅栏后执行的动作
            log.info("所有用户已准备完毕，开始合并订单...");
            // 这里可以添加合并订单逻辑，计算总金额，分配优惠等
        });

        Random random = new Random();
        GroupBuyer.initTotalAmount();
        ExecutorService executor = Executors.newFixedThreadPool(userCount);
        List<GroupBuyer> participants = new ArrayList<>();
        for (int i = 1; i <= userCount; i++) {
            // 每个用户选择购买数量
            int amount = random.nextInt(10) + 1;
            GroupBuyer participant = new GroupBuyer(i, amount, barrier);
            GroupBuyer.addTotalAmount(amount);
            participants.add(participant);
        }

        // 计算每个人占比
        // 开始拼单，每个用户在一个独立的线程中执行
        participants.forEach(executor::execute);

        // 关闭线程池，等待所有任务完成
        executor.shutdown();
    }

}
