package com.volvo.congestion.tax.calculator.controller.mapper;

import com.volvo.congestion.tax.calculator.dto.GetCongestionTaxRequest;
import com.volvo.congestion.tax.calculator.service.command.CongestionTaxCommand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CongestionTaxCommandMapper {

    public static CongestionTaxCommand toCommand(GetCongestionTaxRequest request) {
        return CongestionTaxCommand.builder()
                .vehicleType(request.getVehicleType())
                .dates(request.getDates())
                .build();
    }
}
