package clone;

/**
 * <p> 车辆接口 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/11/30 13:35
 */
public interface Car {
    /**
     * 全驱
     *
     * @return 是否全驱
     */
    default Boolean allWheel() {
        return false;
    }
}