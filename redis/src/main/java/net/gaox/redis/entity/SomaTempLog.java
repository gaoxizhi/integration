package net.gaox.redis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 体感温度湿度计
 * </p>
 *
 * @author gaox
 * @since 2019-04-08
 */
public class SomaTempLog implements Serializable {

    private static final long serialVersionUID = 14646897L;

    private Long id;
    private Long eqId;
    private LocalDateTime updateTime;
    private Float temp;
    private Float hum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEqId() {
        return eqId;
    }

    public void setEqId(Long eqId) {
        this.eqId = eqId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getHum() {
        return hum;
    }

    public void setHum(Float hum) {
        this.hum = hum;
    }


    @Override
    public String toString() {
        return "SomaTempLog{" +
                "id=" + id +
                ", eqId=" + eqId +
                ", updateTime=" + updateTime +
                ", temp=" + temp +
                ", hum=" + hum +
                "}";
    }
}
