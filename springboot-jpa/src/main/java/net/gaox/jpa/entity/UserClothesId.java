package net.gaox.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 用户衣物关联键
 *
 * @author gaox·Eric
 * @date 2019/5/2 23:35
 */
@Embeddable
@Data
public class UserClothesId implements Serializable {
    @Column(name = "userId")
    private Long userId;
    @Column(name = "clothesId")
    private Long clothesId;

    public UserClothesId() {
    }
}