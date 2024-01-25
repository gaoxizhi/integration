package net.gaox.jvm;

/**
 * <p> 初始化类阶段和构造函数影响的类变量 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-24 13:22
 */
public class InitializationClass {

    private static InitializationClass instance = new InitializationClass();
    private static int x = 0;
    private static int y;

    // 1. 在连接阶段的准备过程中，每一个类变量都被赋予了相应的初始值：
    // instance=null, x = 0, y = 0
    // 2. 在类的初始化阶段(new 对象时期)，需要为每一个类赋予程序编写时期所期待的正确的初始值
    // 首先会进入 instance 的构造函数中，执行完 instance 的构造函数之后，各个静态变量的值如下：
    // instance=Singleton@ff00ff00, x = 1, y = 1
    // 3. 然后，为 x 初始化，由于 x 没有显式地进行赋值，因此 0 才是所期望的正确赋值
    // 而 y 由于没有给定初始值，在构造函数中计算所得的值就是所谓的正确赋值，因此结果又会变成：
    // instance=Singleton@3f99bd52, x = 0, y = 1
    private InitializationClass() {
        x++;
        y++;
    }

    public static InitializationClass getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        InitializationClass singleton = InitializationClass.getInstance();
        System.out.println(singleton);
        System.out.println("singleton.x = " + singleton.x);
        System.out.println("singleton.y = " + singleton.y);
    }

}
