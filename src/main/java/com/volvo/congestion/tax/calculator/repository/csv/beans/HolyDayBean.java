package com.volvo.congestion.tax.calculator.repository.csv.beans;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class HolyDayBean {

    @CsvBindByName
    String month;

    @CsvBindByName
    int dayOfMonth;

    @CsvBindByName
    boolean wholeMonth;
}
