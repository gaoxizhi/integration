package net.gaox.jdbc;

/**
 * @Description: <p>  </p>
 * @ClassName Student
 * @Author: gaox·Eric
 * @Date: 2019/3/31 17:27
 */
public class Student {

    private String name;
    private String sex;
    private int age;

    public Student(String name, String sex, int age) {
        super();
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student() {
        super();
    }
}
