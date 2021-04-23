package net.gaox.base;

public class doubleArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * ��ά����ĸ�ֵ
		 * 1.��ʼ��
		 * 2.���θ�ֵ
		 * 3.copy/clone
		 */
		int[][] arr ={{12,54,76,33},{65,78,98,31},{32,71,16,46},{25,46,78,13}};
		
		int[][] arr1 = new int[10][10];
		//����Ŀ�¡ֻ��ǳ���ƣ�ǣһ������ȫ��
		//arr1 = arr.clone();
		//arr = arr1.clone();
		//arr1[9][9] = 10;
		System.out.println("���������");
		for(int[] i :arr){
			for(int j:i){
				System.out.print(j+"  ");
			}
			System.out.println();
		}
		System.out.println("arr.length: "+arr.length);
		System.out.println("arr[1].length: "+arr[1].length);
	}

}
