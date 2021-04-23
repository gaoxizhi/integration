package net.gaox.solution;

public class Sort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = { 12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16 };
		long[] time = new long[4];
		time[0] = (long) System.nanoTime();
		bobSort();
		time[1] = (long) System.nanoTime();
		chooseSort();
		time[2] = (long) System.nanoTime();
		insteaSort();
		time[3] = (long) System.nanoTime();
		
		for(int i = 1;i<4;i++){
			System.out.println(i+"��ʱ��"+(time[i]-time[i-1]));
		}
	}

	private static void insteaSort() {
		/**
		 * ��������
		 */
		int[] array = { 12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16 };

		for (int i = 1; i < array.length; i++) {
			int w = i;
			int temp = array[i];
			int j = i - 1;
			for (; (j >= 0) && (temp < array[j]); j--) {
				// �ڵ�jλ���ҵ���iλ�û�ҪС����
				// �����������֮������ƶ�
				array[j + 1] = array[j];
			}
			// ���������������j��ֵ�Ѿ��ı䣬���û�н�����temp�ָ�ֵ��ԭ����λ��
			array[j + 1] = temp;
			for (int k = 0; k < array.length; k++) {
				System.out.print(array[k] + ",");
			}
			System.out.println();
		}

	}

	private static void chooseSort() {
		/**
		 * ѡ������
		 */
		int[] array = { 12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16 };
		for (int i = 0; i < array.length; i++) {
			int w = i;
			int temp = array[i];
			for (int j = i + 1; j < array.length; j++) {
				if (temp > array[j]) {
					w = j;
					temp = array[j];
				}
			}
			if (w != i) {
				array[w] = array[i];
				array[i] = temp;
			}
			for (int k = 0; k < array.length; k++) {
				System.out.print(array[k] + ",");
			}
			System.out.println();

		}
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}
		System.out.println();
		System.out.println();
	}

	private static void bobSort() {
		/**
		 * ð������ ���αȽϽ����ķŵ����
		 */
		int[] array = { 12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16 };

		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i; j < array.length; j++) {
				if (array[i] > array[j]) {
					int temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
			for (int k = 0; k < array.length; k++) {
				System.out.print(array[k] + ",");
			}
			System.out.println();
		}
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}
		System.out.println();
		System.out.println();
	}

}
