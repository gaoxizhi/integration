package net.gaox.work.thread.service;

import net.gaox.thread.future.Future;

/**
 * <p> 订单接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 11:58
 */
public interface OrderService {

    /**
     * 根据订单编号查询订单明细，有入参也有返回值，但是返回类型必须是 Future
     *
     * @param orderId 订单编号
     * @return 订单明细
     */
    Future<String> findOrderDetails(long orderId);

    /**
     * 提交订单，没有返回值
     *
     * @param account 账号
     * @param orderId 订单号
     */
    void order(String account, long orderId);

}
