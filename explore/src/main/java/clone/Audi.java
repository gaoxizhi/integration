package clone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/11/30 15:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Audi implements Car, Cloneable {
    /**
     * 品牌
     */
    private String brand;
    /**
     * 出厂时间
     */
    private LocalDateTime productionTime;
    /**
     * 价格
     */
    private BigDecimal price;

    @Override
    protected Audi clone() throws CloneNotSupportedException {
        return (Audi) super.clone();
    }

    @Override
    public Boolean allWheel() {
        return true;
    }
}