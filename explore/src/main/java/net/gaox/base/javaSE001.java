package net.gaox.base;

import java.util.Scanner;


public class javaSE001{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// �����㷨��ð�ݣ�ѡ�񣬲���
		
		@SuppressWarnings("resource") 
		Scanner sc = new Scanner(System.in);
		//System.out.println("����������һ���û���Ϣ����ʽ���û��������䣬��ַ���Ա�,���롣");
		String[] headLine  = new String[] { "�û���", "����", "��ַ", "�Ա�", "����" };
		user user1 = new user();
		for (int i = 0; i < headLine.length; i++) {
			System.out.println("�����룺  " + headLine[i]);
			String str = sc.nextLine();
			switch (i) {
			case 0 :user1.setName(str);
				break;
			case 1 :user1.setAge(Integer.parseInt(str));
			break;
			case 2 :user1.setAddr(str);
			break;
			case 3 :user1.setSex(str);
			break;
			case 4 :user1.setPasswd(str);
			break;

			default:
				break;
			}
		}
		
		user1.sayPaly();
	}
}
