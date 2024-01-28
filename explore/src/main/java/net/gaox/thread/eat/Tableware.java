package net.gaox.thread.eat;

/**
 * <p> 餐具 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 14:05
 */
public class Tableware {
    private final String toolName;

    public Tableware(String toolName) {
        this.toolName = toolName;
    }

    @Override
    public String toString() {
        return "Tool:" + toolName;
    }

}
