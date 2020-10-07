package net.gaox.drools.controller;

import net.gaox.drools.entity.TaxiFare;
import net.gaox.drools.entity.TaxiRide;
import net.gaox.drools.service.TaxiFareCalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p> Drools的士计费控制器 </p>
 * 起步价：首3公里12元;
 * 续租价：超过3公里部分，每公里2.6元
 * 返空费实行阶梯附加:
 * - 15至25公里按照续租价加收20%
 * - 25公里以上按续租价加收50%
 *
 * @author gaox·Eric
 * @date 2020/4/3 09:52
 */
@RequestMapping("/taxi")
@RestController
public class TaxiController {

    private final TaxiFareCalculatorService taxiFareCalculatorService;

    public TaxiController(TaxiFareCalculatorService taxiFareCalculatorService) {
        this.taxiFareCalculatorService = taxiFareCalculatorService;
    }

    @GetMapping("/cal")
    public ResponseEntity<Object> calTaxiFare(TaxiRide taxiRide) {
        if (taxiRide.getIsNightSurcharge() == null) {
            taxiRide.setIsNightSurcharge(false);
        }
        if (taxiRide.getDistanceInMile() == null) {
            taxiRide.setDistanceInMile(new BigDecimal(12));
        }
        TaxiFare rideFare = new TaxiFare();
        //Drools计算
        BigDecimal totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        return ResponseEntity.ok(totalCharge.toString());
    }
}