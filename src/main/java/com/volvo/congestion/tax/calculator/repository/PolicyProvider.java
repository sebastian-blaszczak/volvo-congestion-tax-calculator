package com.volvo.congestion.tax.calculator.repository;

import com.volvo.congestion.tax.calculator.domain.PolicyContext;

public interface PolicyProvider {

    PolicyContext getContext(); // this should be cached
}
