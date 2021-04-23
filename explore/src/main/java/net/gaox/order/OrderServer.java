package net.gaox.order;

/**
 * <p> 订单服务接口 </p>
 *
 * @author gaox·Eric
 * @date 2021/3/1 21:10
 */
public interface OrderServer {
    /**
     * 生成全局id
     *
     * @return 全局id
     */
    String getOrderNo();
}
