package net.gaox.order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p> 订单服务抽象类及默认实现 </p>
 *
 * @author gaox·Eric
 * @date 2021/3/1 21:23
 */
public abstract class AbstractOrderServer implements OrderServer {
    private static int num = 0;

    public static synchronized String getOrderNoDefault() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHMMSS");
        return format.format(new Date()) + num++;
    }

    public static synchronized String getOrderNO() {
        return getOrderNoDefault();
    }

}
