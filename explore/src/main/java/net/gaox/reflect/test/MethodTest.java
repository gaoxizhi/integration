package net.gaox.reflect.test;

import net.gaox.reflect.base.Person;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * <p> 获取运行时类的方法结构 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-22 23:36
 */
public class MethodTest {


    /**
     * 获取运行时类的方法结构
     * 权限修饰符  返回值类型  方法名(参数类型1 形参名1,...) throws XxxException{}
     */
    @Test
    public void test1() {
        Class clazz = Person.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method m : declaredMethods) {
            // 1.获取方法声明的注解
            Annotation[] annotations = m.getAnnotations();
            for (Annotation a : annotations) {
                System.out.println("Annotation\t" + a);
            }

            // 2.权限修饰符
            System.out.print(Modifier.toString(m.getModifiers()) + "\t");

            // 3.返回值类型
            System.out.print(m.getReturnType().getName() + "\t");

            // 4.方法名
            System.out.print(m.getName());
            System.out.print("(");
            // 5.形参列表
            Class[] pTs = m.getParameterTypes();
            if (!(pTs == null && pTs.length == 0)) {
                for (int i = 0; i < pTs.length; i++) {
                    if (i == pTs.length - 1) {
                        System.out.print(pTs[i].getName() + " args_" + i);
                        break;
                    }
                    System.out.print(pTs[i].getName() + " args_" + i + ",");
                }
            }
            System.out.print(")");

            // 6.抛出的异常
            Class[] eTs = m.getExceptionTypes();
            if (eTs.length > 0) {
                System.out.print("throws ");
                for (int i = 0; i < eTs.length; i++) {
                    if (i == eTs.length - 1) {
                        System.out.print(eTs[i].getName());
                        break;
                    }
                    System.out.print(eTs[i].getName() + ",");
                }
            }
            System.out.println();
        }
    }
}