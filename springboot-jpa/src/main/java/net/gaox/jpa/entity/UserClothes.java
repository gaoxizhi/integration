package net.gaox.jpa.entity;

import lombok.Data;
import net.gaox.jpa.base.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p> 用户衣物关系表 </p>
 *
 * @author gaox·Eric
 * @date 2019/5/2 23:31
 */
@Data
@Entity
@Table(name = "user_clothes")
@org.hibernate.annotations.Table(appliesTo = "user_clothes", comment = "用户衣物关系表")
public class UserClothes extends AbstractEntity implements Serializable {

    /**
     * 复合主键要用这个注解
     */
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "userId")),
            @AttributeOverride(name = "clothesId", column = @Column(name = "clothesId"))
    })
    private UserClothesId id;

    @Override
    protected void prePersist() {
    }

}