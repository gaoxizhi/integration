package net.gaox.jpa.entity;

import lombok.Data;
import net.gaox.jpa.base.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p> 衣物表 </p>
  Clothes
 * @author gaox·Eric
 * @date 2019/5/2 15:06
 */
@Data
@Entity
@Table(name = "clothes")
@org.hibernate.annotations.Table(appliesTo = "clothes", comment = "衣物表")
public class Clothes extends AbstractEntity implements Serializable {
    @Id
    private Long id;
    @Column(length = 32)
    private String name;
    private Integer size;
    private Integer colors;

    @Override
    protected void prePersist() {
    }

}