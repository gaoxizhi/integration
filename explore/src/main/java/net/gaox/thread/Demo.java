package net.gaox.thread;

public class Demo {

	public static void main(String[] args) throws Exception {
		new SellTicket("A").start();
		new SellTicket("B").start();
		new SellTicket("C").start();
		
		new Thread(new MySell(),"D").start();
		new Thread(new MySell(),"E").start();
		new Thread(new MySell(),"F").start();
		
		for(int i = 10; i > 0; i--){
			System.out.println(i);
			Thread.sleep(100);
		}
	}
}