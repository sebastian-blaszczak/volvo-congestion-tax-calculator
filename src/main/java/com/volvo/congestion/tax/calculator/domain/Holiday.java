package com.volvo.congestion.tax.calculator.domain;

import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;

@Value
public class Holiday {
    Month month;
    int dayOfMonth;
    boolean wholeMonth;

    public static Holiday from(Month month, int dayOfMonth) {
        return new Holiday(month, dayOfMonth, false);
    }

    public static Holiday from(Month month, int dayOfMonth, boolean wholeMonth) {
        return new Holiday(month, dayOfMonth, wholeMonth);
    }

    public boolean isHolyDay(LocalDateTime date) {
        return (month == date.getMonth() && dayOfMonth == date.getDayOfMonth())
                || (month == date.getMonth() && wholeMonth)
                || dayBefore(date);
    }

    private boolean dayBefore(LocalDateTime date) {
        return LocalDate.of(date.getYear(), month, dayOfMonth).minus(Period.ofDays(1)).equals(date.toLocalDate());
    }
}
