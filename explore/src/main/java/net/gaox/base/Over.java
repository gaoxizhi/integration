package net.gaox.base;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2021/4/23 21:47
 */
public class Over extends Base {
    public static void main(String[] args) {
        Over o = new Over();
        o.inMethod();

        int j = 0;
        /// 访问不到Base类的静态方法
        // Base test = new Base();
        // j = test.aMethod();
        System.out.println(j);
    }

    public void inMethod() {
        System.out.println("Over.method");
    }


    public int aMethod() {
        /// 语法错误，非静态方法中包含静态变量
        // static int i=0;
        // i++;
        // return i;
        return 1;
    }
}
