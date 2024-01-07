package net.gaox.reflect.base;

/**
 * <p> Person </p>
 *
 * @author gaox·Eric
 * @date 2023-03-22 22:00
 */
@MyAnnotation(value = "java")
public class Person extends Creature<String> implements Comparable<String>, MyInterface {

    private String name;
    public int age;
    public int id;

    public Person() {
    }

    @MyAnnotation(value = "C++")
    Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @MyAnnotation
    public String show(String nation) {
        System.out.println("我来自" + nation + "星系");
        return nation;
    }

    private static void showDesc() {
        System.out.println("我是 私有静态方法");
    }

    private String showNation(String nation) {
        System.out.println("喷子实在太多了！！！" + nation);
        return nation;
    }

    @Override
    public void info() {
        System.out.println("火星喷子");
    }

    public String display(String interests, int age) {
        return interests + age;
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}