package net.gaox.jpa.base;

import lombok.Data;
import net.gaox.jpa.config.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * <p> 实体类公共对象 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 22:12
 */

@Data
@MappedSuperclass
public abstract class AbstractEntity {

    @Comment("创建时间")
    @Column(name = "create_time", columnDefinition = "datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP")
    protected LocalDateTime createTime;

    @Comment("上次修改时间")
    @Column(name = "update_time", columnDefinition = "datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    protected LocalDateTime updateTime;

    /**
     * 状态：0 删除, 1 正常, 2 禁用, 3 注销
     */
    @Comment("状态: 0 删除, 1 正常, 2 禁用, 3 注销")
    @Column(name = "state", columnDefinition = "tinyint(4) NOT NULL DEFAULT 1")
    protected Integer state;

    @PrePersist
    protected abstract void prePersist();

    @PreUpdate
    protected void preUpdate() {
        updateTime = LocalDateTime.now();
    }

}
