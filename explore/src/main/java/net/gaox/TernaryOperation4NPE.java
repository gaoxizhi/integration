package net.gaox;

/**
 * <p> 三元运算符的NPE </p>
 * 诱发因素：包装类型的<b>过度</b>自动拆箱
 *
 * @author gaox·Eric
 * @date 2021/8/21 22:55
 */
public class TernaryOperation4NPE {

    public static void main(String[] args) {
        Integer a = 10;
        Integer b = 20;
        Integer c = null;

        // 这里会抛出NPE
        // Integer d = false ? a * b : c;
        Integer d = false ? 30 : c;
        System.out.println("拦精灵");
    }

}
