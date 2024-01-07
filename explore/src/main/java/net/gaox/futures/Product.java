package net.gaox.futures;

import lombok.Data;

/**
 * <p> shop </p>
 *
 * @author gaox·Eric
 * @date 2022/8/7 02:47
 */

@Data
public class Product {
    private String name;

    public Product(String name) {
        this.name = name;
    }

}
