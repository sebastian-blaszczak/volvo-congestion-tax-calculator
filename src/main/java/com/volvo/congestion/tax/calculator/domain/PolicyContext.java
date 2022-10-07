package com.volvo.congestion.tax.calculator.domain;

import lombok.Value;

import java.util.List;

@Value
public class PolicyContext {
    List<VehicleType> tollFreeVehicles;
    List<TaxPolicy> taxPolicies;
    List<Holiday> holidays;
    int maxDailyAmount;
}
