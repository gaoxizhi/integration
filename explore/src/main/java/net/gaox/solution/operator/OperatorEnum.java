package net.gaox.solution.operator;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/10/9 12:35
 */
public enum OperatorEnum {
    /**
     * 加减乘除 操作符
     */
    ADD('+', 1),
    SUBTRACT('-', 1),
    MULTIPLY('*', 2),
    DIVIDE('/', 2),
    /**
     * 括号优先级最高
     */
    LEFT_BRACKET('(', 3),
    RIGHT_BRACKET(')', 3);
    char value;
    int priority;

    OperatorEnum(char value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    /**
     * 比较两个符号的优先级
     *
     * @param c1 字符1
     * @param c2 字符2
     * @return c1的优先级是否比c2的高，高则返回正数，等于返回0，小于返回负数
     */
    public static int cmp(char c1, char c2) {
        int p1 = 0;
        int p2 = 0;
        for (OperatorEnum o : OperatorEnum.values()) {
            if (o.value == c1) {
                p1 = o.priority;
            }
            if (o.value == c2) {
                p2 = o.priority;
            }
        }
        return p1 - p2;
    }

    /**
     * 枚举出来的才视为运算符，用于扩展
     *
     * @param c 字符
     * @return 是否运算符
     */
    public static boolean isOperator(char c) {
        for (OperatorEnum o : OperatorEnum.values()) {
            if (o.value == c) {
                return true;
            }
        }
        return false;
    }
}