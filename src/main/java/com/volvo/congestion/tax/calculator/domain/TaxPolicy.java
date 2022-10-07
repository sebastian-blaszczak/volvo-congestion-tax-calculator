package com.volvo.congestion.tax.calculator.domain;

import lombok.Value;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Value(staticConstructor = "from")
public class TaxPolicy {
    LocalTime from;
    LocalTime to;
    int value;

    public boolean canBeApplied(LocalDateTime date) {
        LocalTime time = date.toLocalTime();
        return (from.equals(time) || from.isBefore(time))
                && (to.isAfter(time) || to.equals(time));
    }
    
}
