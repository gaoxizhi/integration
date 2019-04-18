package net.gaox.redis.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 家庭表
 * </p>
 *
 * @author gaox
 * @since 2019-04-07
 */
public class Home implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long createUserId;
    private String alias;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer able;

    @Override
    public String toString() {
        return "Home{" +
                "id=" + id +
                ", createUserId=" + createUserId +
                ", alias='" + alias + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", able=" + able +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAble() {
        return able;
    }

    public void setAble(Integer able) {
        this.able = able;
    }

}
