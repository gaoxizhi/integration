package net.gaox.thread;

public class MySell implements Runnable {
	private int num = 50;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= num; i++) {
			System.out.println(Thread.currentThread().getName() + "�����˵�" + i
					+ "��Ʊ��");
		}
	}
}
