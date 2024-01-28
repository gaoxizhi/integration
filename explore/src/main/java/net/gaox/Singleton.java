package net.gaox;

/**
 * <p> Holder + 枚举实现线程安全的单例 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-27 16:03
 */
public class Singleton {

    /**
     * 实例变量
     */
    private byte[] data = new byte[1024];

    private Singleton() {
    }

    private enum EnumHolder {
        INSTANCE;
        private Singleton instance;

        EnumHolder() {
            this.instance = new Singleton();
        }

        private Singleton getSingleton() {
            return instance;
        }
    }

    public static Singleton getInstance() {
        return EnumHolder.INSTANCE.getSingleton();
    }

}
