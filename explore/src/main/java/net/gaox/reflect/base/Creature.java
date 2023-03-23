package net.gaox.reflect.base;

import java.io.Serializable;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2023-03-22 22:02
 */

public abstract class Creature<T> implements Serializable {
    private char gender;
    public double weight;

    private void breath() {
        System.out.println("太阳系");
    }

    public void eat() {
        System.out.println("银河系");
    }
}
