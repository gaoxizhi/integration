package net.gaox.base;

import java.util.*;

public class Statistical_characters {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new һ��HashMap��Ϊ�ַ�����ֵΪͳ�Ƶ�����
		HashMap<Character,Integer> hm=new HashMap<Character,Integer>();
		System.out.print("������һ�仰��");
		Scanner sc=new Scanner(System.in);
		String words=sc.nextLine();
		//����仰�е������ַ��ĸ������Դ洢����
		for(int i=0;i<words.length();i++){
			char c=words.charAt(i);
			//�����map��û������ַ������һ����ֵ�ԣ���Ϊ���ַ���ֵΪ1
			if(!hm.containsKey(c)){
				hm.put(c, 1);
			} else{
				//����鵽���ַ��Ѿ����ڣ�ȡ����ֵ��+1�ٴ���
				hm.put(c, hm.get(c)+1);
			}
		}
		//�����м�ֵ�洢������count
	   Collection<Integer> count=hm.values();
	   //�ҳ���������Ǹ���
	   int maxCount=Collections.max(count);
	   //�������ֵȥ������Ӧ��keyֵ--�ַ�
	   System.out.println("���ִ��������ַ��ǣ�");
	   
	   Set<Map.Entry<Character, Integer>> set=hm.entrySet();
	   for(Map.Entry<Character, Integer> i:set){
		   if(i.getValue()==maxCount){
			   System.out.println(i.getKey()+"�����ǣ�"+maxCount);
			   
		   }
	   }
		

	}
}
