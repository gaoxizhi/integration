package net.gaox.thread.flight;

/**
 * <p> 安检流程类 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 14:30
 */
public class FlightSecurity {

    /**
     * 安检计数
     */
    private int count = 0;

    /**
     * 登机牌
     */
    private String boardingPass = "null";

    /**
     * 身份证
     */
    private String idCard = "null";

    /**
     * 进入安检
     * 在 FlightSecurity 中的属性赋值的时候会出现多个线程交错的情况，需要对共享资源增加同步保护
     *
     * @param boardingPass 登机牌
     * @param idCard       身份证
     */
    public /*synchronized*/ void pass(String boardingPass, String idCard) {
        this.boardingPass = boardingPass;
        this.idCard = idCard;
        this.count++;
        check();
    }

    /**
     * 安检流程
     */
    private void check() {
        if (boardingPass.charAt(0) != idCard.charAt(0)) {
            throw new RuntimeException("====Exception====" + this);
        }
    }

    public String toString() {
        return "The " + count + " passengers, boardingPass [" + boardingPass + "], idCard [" + idCard + "]";
    }

}
