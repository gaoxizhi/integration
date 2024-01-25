package net.gaox.jvm;

/**
 * <p> 初始化类阶段和构造函数影响的类变量1 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-24 13:22
 */
public class InitializationClass1 {

    private static int x = 0;
    private static int y;
    private static InitializationClass1 instance = new InitializationClass1();

    // 1. 在连接阶段的准备过程中，每一个类变量都被赋予了相应的初始值：
    // x = 0, y = 0, instance=null
    // 跳过解析过程
    // 2. 类的初始化阶段，初始化阶段会为每一个类变量赋予正确的值，也就是执行 <clinit>（）方法的过程：
    // x = 0, y=0, instance = new Singleton()
    // 在 new Singleton() 的时候会执行类的构造函数，而在构造函数中分别对 x 和 y 进行了自增，因此结果为：
    // x = 1, y = 1

    // 如果你在构造函数中对属性进行了初始化，那么这个初始化的值会覆盖属性的显式初始化值
    // 因此，如果你想要使用属性的显式初始化值，你需要在构造函数中避免对属性进行初始化操作
    private InitializationClass1() {
        x++;
        y++;
    }

    public static InitializationClass1 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        InitializationClass1 singleton = InitializationClass1.getInstance();
        System.out.println(singleton);
        System.out.println("singleton.x = " + singleton.x);
        System.out.println("singleton.y = " + singleton.y);
    }

}
