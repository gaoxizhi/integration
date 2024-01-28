package net.gaox.thread.eat;

/**
 * <p> 完整餐具 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 14:09
 */
public class TablewarePair {
    private final Tableware leftTool;

    private final Tableware rightTool;

    public TablewarePair(Tableware leftTool, Tableware rightTool) {
        this.leftTool = leftTool;
        this.rightTool = rightTool;
    }

    public Tableware getLeftTool() {
        return leftTool;
    }

    public Tableware getRightTool() {
        return rightTool;
    }

}
