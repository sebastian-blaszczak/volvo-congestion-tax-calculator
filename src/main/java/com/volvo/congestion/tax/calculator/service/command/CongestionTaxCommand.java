package com.volvo.congestion.tax.calculator.service.command;

import com.volvo.congestion.tax.calculator.domain.VehicleType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class CongestionTaxCommand {
    VehicleType vehicleType;
    List<LocalDateTime> dates;
}
