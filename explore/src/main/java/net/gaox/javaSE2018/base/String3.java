package net.gaox.javaSE2018.base;

/**
 * @Description: <p>  </p>
 * @ClassName String3
 * @Author: gaox·Eric
 * @Date: 2019/3/31 19:04
 */
public class String3 {


    /**
     * String类的对象是不可以改变，任何对String的改动都会引发申请新的对象
     * StringBuffer和StringBuilder的对象都能够被多次修改，修改的还是对象本身
     *
     * 线程安全性：
     * StringBuilder，不支持并发，线程不安全。
     * StringBuffer ，安全。多线程，高并发。
     * String       ，安全。
     *
     * 总结：
     * 少量数据，不经常改动使用String
     * 大量易变动的数据使用StringBuffer
     * 不要求线程安全的可以使用StirngBulider
     *
     *
     *
     * 主要方法：append(任何类型数据)、inster(位置，数据)
     */
}
