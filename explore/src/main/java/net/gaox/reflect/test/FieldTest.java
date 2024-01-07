package net.gaox.reflect.test;


import net.gaox.reflect.base.Person;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * <p> 获取当前运行时类的：属性结构，权限修饰符，数据类型，变量名 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-22 23:26
 */

public class FieldTest {

    /**
     * 获取当前运行时类的属性结构
     */
    @Test
    public void test() {
        Class clazz = Person.class;
        // getFields(): 获取当前运行时类及其父类中声明为public访问权限的属性
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            System.out.println(f);
        }
        System.out.println("++++++++++++++++++");

        // getDeclaredFields(): 获取当前运行时类中声明的所有属性（不包含父类中声明的属性）
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            System.out.println(f);
        }
    }

    /**
     * 获取当前运行时类的：权限修饰符，数据类型，变量名
     */
    @Test
    public void test2() {
        Class clazz = Person.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            // 1.权限修饰符
            int modifier = f.getModifiers();
            System.out.print(Modifier.toString(modifier) + "\t");

            // 2.数据类型
            Class type = f.getType();
            System.out.print(type.getName() + "\t");

            // 3.变量名
            String fName = f.getName();
            System.out.print(fName);
            System.out.println();
            System.out.println("***************************");
        }
    }
}