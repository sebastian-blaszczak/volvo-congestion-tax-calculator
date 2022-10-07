package com.volvo.congestion.tax.calculator.service.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDataUtils {

    public static LocalDateTime toDate(String text) {
        return LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
    }
}
