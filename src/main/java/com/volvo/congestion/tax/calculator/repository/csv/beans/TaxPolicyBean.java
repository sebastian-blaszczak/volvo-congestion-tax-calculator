package com.volvo.congestion.tax.calculator.repository.csv.beans;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class TaxPolicyBean {
    @CsvBindByName
    int hourFrom;

    @CsvBindByName
    int minutesFrom;

    @CsvBindByName
    int hourTo;
    
    @CsvBindByName
    int minutesTo;

    @CsvBindByName
    int tax;
}
