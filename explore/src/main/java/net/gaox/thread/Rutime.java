package net.gaox.thread;

/**
 * @Description: <p>  </p>
 * @ClassName Rutime
 * @Author: gaox·Eric
 * @Date: 2019/3/31 18:52
 */
public class Rutime extends Thread {

    private Integer i = 0;

    @Override
    public void run() {
        if (i < 100) {
            if (i % 10 == 0) {
                System.out.println();
            }
            System.out.print(i + "\t");
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
