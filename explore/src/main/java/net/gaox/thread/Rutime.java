package net.gaox.thread;

public class Rutime extends Thread {

	private Integer i = 0;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (i < 100) {
			if(i%10 == 0){
				System.out.println();
			}
			System.out.print(i + "\t" );
			i++;
		}
	}

	public static void main(String[] args) {
		Rutime r1 = new Rutime();
		Rutime r2 = new Rutime();
		while (true) {
			r1.run();
			r2.run();
		}
	}

}
