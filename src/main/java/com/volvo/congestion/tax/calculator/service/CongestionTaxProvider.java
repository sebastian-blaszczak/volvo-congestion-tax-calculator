package com.volvo.congestion.tax.calculator.service;

import com.volvo.congestion.tax.calculator.dto.TaxDto;
import com.volvo.congestion.tax.calculator.service.command.CongestionTaxCommand;

public interface CongestionTaxProvider {

    TaxDto getTax(CongestionTaxCommand command);
}
