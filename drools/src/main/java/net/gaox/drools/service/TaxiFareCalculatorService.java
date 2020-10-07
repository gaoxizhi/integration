package net.gaox.drools.service;

import net.gaox.drools.entity.TaxiFare;
import net.gaox.drools.entity.TaxiRide;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p> 出租车service </p>
 *
 * @author gaox·Eric
 * @date 2020/4/3 17:51
 */
@Service
public class TaxiFareCalculatorService {
    private final KieContainer kieContainer;

    public TaxiFareCalculatorService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public BigDecimal calculateFare(TaxiRide taxiRide, TaxiFare rideFare) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();
        return rideFare.total();
    }
}