package net.gaox.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dateOutput {
	/**
	 * yyyy���� MM���� dd���� 
	 * hh��1~12Сʱ��(1-12) 
	 * HH��24Сʱ��(0-23) 
	 * mm���� ss���� S������ 
	 * E�����ڼ�
	 * D��һ���еĵڼ���
	 * F��һ���еĵڼ�������(���������ܹ�������������7) 
	 * w��һ���еĵڼ�������
	 * W��һ���еĵڼ�����(�����ʵ���������)
	 * a���������ʶ 
	 * k����HH��࣬��ʾһ��24Сʱ��(1-24)��
	 * K����hh����ʾһ��12Сʱ��(0-11) 
	 * z����ʾʱ��
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		//ʱ���ʽ
		SimpleDateFormat sdf = new SimpleDateFormat("yy��MM��dd�� HHʱmm��ss��\n "
				+ "�����w�� ʱ����z a E");
		//��ȡϵͳ��ǰʱ�䣬��ת�����
		Date data = new Date();
		System.out.println("���ϵͳʱ��:\n"+sdf.format(data));
		//���ַ���ת����ʱ��
		String str = "2015/09/12 12:30:21";
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = sdf2.parse(str);// String->Date����SimpleDateFormat��parse ()����

		System.out.println("string To Date:\n"+sdf.format(date).toString());
		

		
	}
}
