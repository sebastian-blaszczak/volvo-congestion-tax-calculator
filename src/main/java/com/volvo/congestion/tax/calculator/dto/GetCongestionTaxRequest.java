package com.volvo.congestion.tax.calculator.dto;

import com.volvo.congestion.tax.calculator.domain.VehicleType;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class GetCongestionTaxRequest {
    VehicleType vehicleType;
    List<LocalDateTime> dates;
}
