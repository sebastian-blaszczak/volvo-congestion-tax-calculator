package com.volvo.congestion.tax.calculator.controller;

import com.volvo.congestion.tax.calculator.controller.mapper.CongestionTaxCommandMapper;
import com.volvo.congestion.tax.calculator.dto.GetCongestionTaxRequest;
import com.volvo.congestion.tax.calculator.dto.TaxDto;
import com.volvo.congestion.tax.calculator.service.CongestionTaxProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/congestion-tax")
@RequiredArgsConstructor
public class GetCongestionTaxController {

    private final CongestionTaxProvider congestionTaxProvider;

    @PostMapping
    public TaxDto getCongestionTax(@RequestBody GetCongestionTaxRequest request) {
        return congestionTaxProvider.getTax(CongestionTaxCommandMapper.toCommand(request));
    }
}
