package net.gaox.entity;

import java.math.BigDecimal;

/**
 * @Description: <p>  </p>
 * @ClassName Apple
 * @Author: gaox·Eric
 * @Date: 2019/3/31 16:01
 */
public class Apple {
    private Integer id;
    private String name;
    private BigDecimal money;
    private Integer num;

    public Apple(Integer id, String name, BigDecimal money, Integer num) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.num = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Apple() {
        super();
    }

    @Override
    public String toString() {
        return "Id = " + this.id + ";name = " + this.name + ";money = " + this.money + ";num = " + this.num;
    }
}
