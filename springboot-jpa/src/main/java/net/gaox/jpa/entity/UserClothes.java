package net.gaox.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description: <p> 用户衣物关系表 </p>
 * 用例：联合主键场景
 * @ClassName UserClothes
 * @Author: gaox·Eric
 * @Date: 2019/5/2 23:31
 */
@Data
@Entity
@IdClass(UserClothesId.class)
@Table(name = "user_clothes")
public class UserClothes {
    /**
     * 复合主键要用这个注解
     */
//    @EmbeddedId
//    private UserClothesId id;

    @Id
    private Long userId;
    @Id
    private Long clothesId;

    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "mod_time")
    private LocalDateTime modTime;

}