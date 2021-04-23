package net.gaox.base;

import java.util.Scanner;

public class yanghuisanjiao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int line;
		Scanner scanner = new Scanner(System.in);
		line = scanner.nextInt();
		// ��ʼ������
		int[][] yharray = new int[line][line + 1];
		yharray[0][0] = 1;
		for (int i = 0; i < line; i++) {
			yharray[i][0] = 1;
			yharray[i][i]=1;
			for(int j =1;j< i;j++){
				
			}
		}
		for (int i = 0; i < line; i++) {
			// ��ӡ�Ʊ��
			for (int j = 0; j < (line - i); j++) {
				System.out.print("\t");
			}
			// ��ӡ��ֵ
			for (int j = 0; j < i + 1; j++) {
				if (i == 0) {
					System.out.print("1");
				} else {
					System.out.print("");
				}
			}
			System.out.println();
		}
	}

}
