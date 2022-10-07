package com.volvo.congestion.tax.calculator.dto;

import lombok.Value;

@Value(staticConstructor = "from")
public class TaxDto {
    int value;
    String currency;

    public static TaxDto empty(String currency) {
        return new TaxDto(0, currency);
    }
}
