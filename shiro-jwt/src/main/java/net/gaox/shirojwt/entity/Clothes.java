package net.gaox.shirojwt.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: <p> 衣物表 </p>
 * @ClassName Clothes
 * @Author: gaox·Eric
 * @Date: 2019/5/2 15:06
 */
@Data
@Entity
@Table(name = "clothes")
public class Clothes {
    @Id
    private Long id;
    @Column(length = 32)
    private String name;
    private Integer size;
    private Integer colors;
    private Boolean state;
}