package net.gaox.javaSE2018.base;

/**
 * @Description: <p>  </p>
 * @ClassName User
 * @Author: gaox·Eric
 * @Date: 2019/3/31 16:13
 */
public class User {

    private String name;
    private int age;
    private String addr;
    private String sex;
    private String passwd;

    public User() {
    }

    public User(String Name, int Age, String Addr, String Sex, String Passwd) {
        this.name = Name;
        this.age = Age;
        this.addr = Addr;
        this.sex = Sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void sayPaly() {
        System.out.println("下面输出用户：" + this.getName() + "  的信息：");
        System.out.println("用户名: " + this.getName());
        System.out.println("年龄    : " + this.getAge());
        System.out.println("地址    : " + this.getAddr());
        System.out.println("性别    : " + this.getSex());
        System.out.println("密码    : " + this.getPasswd());
    }
}

