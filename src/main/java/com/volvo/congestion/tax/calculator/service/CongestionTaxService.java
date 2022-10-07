package com.volvo.congestion.tax.calculator.service;

import com.volvo.congestion.tax.calculator.domain.PolicyContext;
import com.volvo.congestion.tax.calculator.domain.Tax;
import com.volvo.congestion.tax.calculator.dto.TaxDto;
import com.volvo.congestion.tax.calculator.repository.PolicyProvider;
import com.volvo.congestion.tax.calculator.service.calculator.CongestionTaxCalculator;
import com.volvo.congestion.tax.calculator.service.command.CongestionTaxCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CongestionTaxService implements CongestionTaxProvider {

    @Value("${tax.currency}")
    String currency;
    private final CongestionTaxCalculator congestionTaxCalculator;
    private final PolicyProvider policyProvider;

    public TaxDto getTax(CongestionTaxCommand command) {
        PolicyContext context = policyProvider.getContext();
        if (isVehicleTollFree(command, context)) {
            return TaxDto.empty(currency);
        }

        Tax tax = congestionTaxCalculator.calculateTax(command.getDates());
        return TaxDto.from(tax.getValue(), currency);
    }

    private boolean isVehicleTollFree(CongestionTaxCommand command, PolicyContext context) {
        return context.getTollFreeVehicles().contains(command.getVehicleType());
    }

}
