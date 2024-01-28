package net.gaox.thread.eat;

/**
 * <p> 吃面问题 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 14:22
 */
public class EatTest {
    public static void main(String[] args) {
        Tableware fork = new Tableware("fork");
        Tableware knife = new Tableware("knife");
        TablewarePair tablewarePair = new TablewarePair(fork, knife);
        new EatNoodleThread("A", tablewarePair).start();
        new EatNoodleThread("B", tablewarePair).start();
    }

}
