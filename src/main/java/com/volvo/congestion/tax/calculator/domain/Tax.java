package com.volvo.congestion.tax.calculator.domain;

import lombok.Value;

@Value(staticConstructor = "from")
public class Tax {
    int value;
}
