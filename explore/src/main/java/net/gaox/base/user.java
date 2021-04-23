package net.gaox.base;

public class user {

	private String name;
	private int age;
	private String addr;
	private String sex;
	private String passwd;
	
	public user(){
	}
	public user(String Name,int Age,String Addr,String Sex,String Passwd){
		this.name = Name;
		this.age  = Age ;
		this.addr = Addr;
		this.sex  = Sex ;
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
		System.out.println("��������û���" + this.getName() + "  ����Ϣ��");
		System.out.println("�û���: "+this.getName());
		System.out.println("����    : "+this.getAge());
		System.out.println("��ַ    : "+this.getAddr());
		System.out.println("�Ա�    : "+this.getSex());
		System.out.println("����    : "+this.getPasswd());
	}
}
