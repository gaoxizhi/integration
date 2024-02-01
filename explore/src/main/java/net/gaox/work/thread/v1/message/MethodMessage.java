package net.gaox.work.thread.v1.message;

import net.gaox.work.thread.service.OrderService;

import java.util.Map;

/**
 * <p> 异步消息抽象类 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 12:20
 */
public abstract class MethodMessage {

    /**
     * 用于收集方法参数，如果又返回 Future 类型则一并收集
     */
    protected final Map<String, Object> params;

    /**
     * 实现类的执行方法
     */
    protected final OrderService orderService;

    public MethodMessage(Map<String, Object> params, OrderService orderService) {
        this.params = params;
        this.orderService = orderService;
    }

    /**
     * 抽象执行方法
     */
    public abstract void execute();

}
