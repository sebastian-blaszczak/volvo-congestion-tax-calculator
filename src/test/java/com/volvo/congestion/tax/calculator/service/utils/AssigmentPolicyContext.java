package com.volvo.congestion.tax.calculator.service.utils;

import com.volvo.congestion.tax.calculator.domain.Holiday;
import com.volvo.congestion.tax.calculator.domain.PolicyContext;
import com.volvo.congestion.tax.calculator.domain.TaxPolicy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

import static com.volvo.congestion.tax.calculator.domain.VehicleType.*;
import static java.time.Month.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssigmentPolicyContext {
    public static PolicyContext context() {
        return new PolicyContext(
                List.of(EMERGENCY_VEHICLE, BUS, DIPLOMAT_VEHICLE, MOTORCYCLE, MILITARY_VEHICLE, FOREIGN_VEHICLE),
                List.of(TaxPolicy.from(LocalTime.of(0, 0), LocalTime.of(5, 59), 0),
                        TaxPolicy.from(LocalTime.of(6, 0), LocalTime.of(6, 29), 8),
                        TaxPolicy.from(LocalTime.of(6, 30), LocalTime.of(6, 59), 13),
                        TaxPolicy.from(LocalTime.of(7, 0), LocalTime.of(7, 59), 18),
                        TaxPolicy.from(LocalTime.of(8, 0), LocalTime.of(8, 29), 13),
                        TaxPolicy.from(LocalTime.of(8, 30), LocalTime.of(14, 59), 8),
                        TaxPolicy.from(LocalTime.of(15, 0), LocalTime.of(15, 29), 13),
                        TaxPolicy.from(LocalTime.of(15, 30), LocalTime.of(16, 59), 18),
                        TaxPolicy.from(LocalTime.of(17, 0), LocalTime.of(17, 59), 13),
                        TaxPolicy.from(LocalTime.of(18, 0), LocalTime.of(18, 29), 8),
                        TaxPolicy.from(LocalTime.of(18, 30), LocalTime.of(23, 59), 0)),
                List.of(Holiday.from(JANUARY, 1),
                        Holiday.from(MARCH, 28),
                        Holiday.from(MARCH, 29),
                        Holiday.from(APRIL, 1),
                        Holiday.from(APRIL, 30),
                        Holiday.from(MAY, 1),
                        Holiday.from(MAY, 8),
                        Holiday.from(MAY, 9),
                        Holiday.from(JUNE, 5),
                        Holiday.from(JUNE, 8),
                        Holiday.from(JUNE, 21),
                        Holiday.from(JULY, 1, true),
                        Holiday.from(NOVEMBER, 1),
                        Holiday.from(DECEMBER, 24),
                        Holiday.from(DECEMBER, 25),
                        Holiday.from(DECEMBER, 26)),
                60
        );
    }
}
