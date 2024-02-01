package net.gaox.thread.production;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> 风扇产品类 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 17:57
 */
@Slf4j
public class FanProduction extends AbstractInstructionBook {

    public FanProduction(int prodId) {
        super(prodId);
    }

    @Override
    protected void firstProcess() {
        log.info("execute the {} first process", getProdId());
    }

    @Override
    protected void secondProcess() {
        log.info("execute the {} second process", getProdId());
    }

    @Override
    public String toString() {
        return "FanProduction ProdId: " + getProdId();
    }

}
