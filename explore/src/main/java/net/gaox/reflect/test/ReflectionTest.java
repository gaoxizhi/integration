package net.gaox.reflect.test;

import net.gaox.reflect.base.Person;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p> 反射 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-22 22:13
 */

public class ReflectionTest {

    /**
     * 反射之前，对于Person的操作
     */
    @Test
    public void test() {

        // 1.创建类的对象
        Person p1 = new Person("gaox", 21);

        // 2.调用对象,调用其内部的属性和方法
        p1.age = 15;
        System.out.println(p1.toString());

        p1.show("CCA");

        // 在Person类的外部，不可以通过Person类的对象调用其内部私有的结构。
        // 比如：name、showNation以及私有的构造器。

    }

    /**
     * 反射 对 Person 的操作
     *
     * @throws NoSuchMethodException     e
     * @throws IllegalAccessException    e
     * @throws InvocationTargetException e
     * @throws InstantiationException    e
     * @throws NoSuchFieldException      e
     */
    @Test
    public void test1() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, NoSuchFieldException {

        Class clazz = Person.class;

        // 1.通过反射，创建Person类的对象
        Constructor cons = clazz.getConstructor(String.class, int.class);
        Object obj = cons.newInstance("Jon", 18);
        Person p = (Person) obj;
        System.out.println(p.toString());

        // 2.通过反射，调用对象指定的属性和方法

        // 调用属性
        Field age = clazz.getDeclaredField("age");
        age.set(p, 10);
        System.out.println(p.toString());

        // 调用方法
        Method show = clazz.getDeclaredMethod("show", String.class);
        show.invoke(p, "CCA");

    }

    @Test
    public void test2() throws Exception {

        Class clazz = Person.class;

        // 通过反射，是可以调用Person类的私有结构的。比如：私有的构造器、方法、属性
        // 调用私有的构造器
        Constructor cons2 = clazz.getDeclaredConstructor(String.class);
        cons2.setAccessible(true);
        Person p1 = (Person) cons2.newInstance("kalo");
        System.out.println(p1);

        // 调用私有的属性
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(p1, "Taoyao");
        System.out.println(p1);

        // 调用私有的方法
        Method showNation = clazz.getDeclaredMethod("showNation", String.class);
        showNation.setAccessible(true);
        String nation = (String) showNation.invoke(p1, "FaceBook");
        // 相当于String nation = p1.showNation("FaceBook")
        System.out.println(nation);
    }

    /**
     * 疑问1：通过直接new的方式或反射的方式都可以调用公共的结构，开发中到底用那个？
     * 建议：直接new的方式。
     * 什么时候会使用：反射的方式。 反射的特征：动态性
     * 疑问2：反射机制与面向对象中的封装性是不是矛盾的？如何看待两个技术？
     * 不矛盾。
     */


    /**
     * 2.换句话说，Class的实例就对应着一个运行时类。
     * 3.加载到内存中的运行时类，会缓存一定的时间。在此时间之内，我们可以通过不同的方式来获取此运行时类。
     */
    @Test
    public void test3() throws ClassNotFoundException {
        // 方式一
        Class c1 = Person.class;
        System.out.println(c1);

        // 方式二 通过运行时类的对象,调用getClass()
        Person p1 = new Person();
        Class c2 = p1.getClass();
        System.out.println(c2);

        // 方式三 调用Class的静态方法：forName(String classPath)
        Class c3 = Class.forName("net.gaox.reflect.base.Person");
        System.out.println(c3);

        // 方式四：使用类的加载器：ClassLoader  (了解)
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        Class c4 = classLoader.loadClass("net.gaox.reflect.base.Person");
        System.out.println(c4);

        System.out.println(c1 == c2 && c1 == c3 && c1 == c4);
    }


    // 万事万物皆对象？对象.xxx,File,URL,反射,前端、数据库操作

    /**
     * Class实例可以是哪些结构的说明
     */
    @Test
    public void test4() {
        Class s1 = Object.class;
        Class s2 = Comparable.class;
        Class s3 = String[].class;
        Class s4 = int[][].class;
        Class s5 = ElementType.class;
        Class s6 = Override.class;
        Class s7 = int.class;
        Class s8 = void.class;
        Class s9 = Class.class;

        int[] a = new int[10];
        int[] b = new int[100];
        Class s10 = a.getClass();
        Class s11 = b.getClass();
        // 只要数组的元素类型与维度一样，就是同一个Class
        System.out.println(s10 == s11);
    }
}