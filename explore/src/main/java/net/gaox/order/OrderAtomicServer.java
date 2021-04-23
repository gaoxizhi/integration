package net.gaox.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> 使用Atomic类型重写 </p>
 *
 * @author gaox·Eric
 * @date 2021/3/1 21:11
 */
public class OrderAtomicServer implements OrderServer {
    static AtomicInteger num = new AtomicInteger();

    /**
     * 重写抽象类的getOrderNo方法
     * 重复id的原因：多态的覆盖，其抽象类接口是static，此处覆盖了父类的实现
     *
     * @return 唯一id
     */
    @Override
    public String getOrderNo() {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHMMSS");
        return format.format(new Date()) + num.incrementAndGet();
    }
}
