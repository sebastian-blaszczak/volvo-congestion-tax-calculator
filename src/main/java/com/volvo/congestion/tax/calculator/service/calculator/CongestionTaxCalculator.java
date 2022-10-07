package com.volvo.congestion.tax.calculator.service.calculator;

import com.volvo.congestion.tax.calculator.domain.Tax;

import java.time.LocalDateTime;
import java.util.List;

public interface CongestionTaxCalculator {

    Tax calculateTax(List<LocalDateTime> dates);
}
