package net.gaox.order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p> 静态方式 </p>
 *
 * @author gaox·Eric
 * @date 2021/3/1 21:11
 */
public class OrderLockServer extends AbstractOrderServer implements OrderServer {
    private static int num = 0;

    /**
     * 重写getOrderNo方法，覆盖了抽象类的实现
     * 重复id的原因：多态的覆盖，其抽象类接口是static，此处覆盖了父类的实现
     *
     * @return 唯一id
     */
    @Override
    public String getOrderNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHMMSS");
        return format.format(new Date()) + num++;
    }

}
