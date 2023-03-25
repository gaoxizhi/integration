package net.gaox.reflect.test;

import net.gaox.reflect.base.Person;
import org.junit.Test;

import java.util.Random;

/**
 * <p> 通过反射创建对应的运行时类的对象 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-22 22:50
 */
public class NewInstanceTest {


    /**
     * newInstance():调用此方法，创建对应的运行时类的对象。内部调用了运行时类的空参的构造器。
     * <p>
     * 要想此方法正常的创建运行时类的对象，要求：
     * 1.运行时类必须提供空参的构造器
     * 2.空参的构造器的访问权限得够。通常，设置为public。
     * <p>
     * 在javabean中要求提供一个public的空参构造器。原因：
     * 1.便于通过反射，创建运行时类的对象
     * 2.便于子类继承此运行时类时，默认调用super()时，保证父类有此构造器
     */
    @Test
    public void test() throws Exception {
        Class<Person> clazz = Person.class;
        Person obj = clazz.newInstance();
        System.out.println(obj);
    }

    @Test
    public void test2() {
        for (int i = 0; i < 100; i++) {
            int num = new Random().nextInt(3);
            String classPath = "";
            switch (num) {
                case 0:
                    classPath = "java.util.Date";
                    break;
                case 1:
                    classPath = "java.lang.Object";
                    break;
                case 2:
                    classPath = "reflect.base.Person";
                    break;
            }
            try {
                Object obj = getInstance(classPath);
                System.out.println(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建一个指定类的对象
     *
     * @param classPath 指定类的全类名
     * @return 类对象
     * @throws Exception e
     */
    public Object getInstance(String classPath) throws Exception {
        Class clazz = Class.forName(classPath);
        return clazz.newInstance();
    }
}