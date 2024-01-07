package net.gaox.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gaox.jpa.base.AbstractEntity;
import net.gaox.jpa.config.annotation.Comment;
import net.gaox.jpa.enums.StateEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p> 用户表 </p>
 *
 * @author gaox·Eric
 * @date 2019/5/2 15:01
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
@org.hibernate.annotations.Table(appliesTo = "user", comment = "用户表")
public class User extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -72851839802068103L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 32, unique = true, nullable = false)
    @Comment("用户名")
    private String name;

    @Column(name = "spCode", length = 32)
    @Comment("code")
    private String spCode;

    @Override
    protected void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        id = null;
        createTime = createTime == null ? now : createTime;
        updateTime = now;
        if (null == state) {
            state = StateEnum.NORMAL.getCode();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(name, user.name)
                && Objects.equals(spCode, user.spCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, spCode);
    }

}