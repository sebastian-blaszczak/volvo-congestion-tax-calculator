package com.volvo.congestion.tax.calculator.repository.csv;

import com.volvo.congestion.tax.calculator.domain.PolicyContext;
import com.volvo.congestion.tax.calculator.domain.VehicleType;
import com.volvo.congestion.tax.calculator.repository.PolicyProvider;
import com.volvo.congestion.tax.calculator.repository.csv.beans.HolyDayBean;
import com.volvo.congestion.tax.calculator.repository.csv.beans.TaxPolicyBean;
import com.volvo.congestion.tax.calculator.repository.csv.mapper.PolicyContextMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CsvPolicyProvider implements PolicyProvider {

    private final CsvParser csvParser;

    @Override
    public PolicyContext getContext() {
        List<HolyDayBean> holidays = getHolidays();
        List<TaxPolicyBean> policies = getPolicies();
        int maxDailyAmount = getDailyAmount();
        List<VehicleType> tollFreeVehicleTypes = getTollFreVehicleTypes();
        return PolicyContextMapper.toContext(holidays, policies, maxDailyAmount, tollFreeVehicleTypes);
    }

    private List<HolyDayBean> getHolidays() {
        return csvParser.getBeans("holidays.csv", HolyDayBean.class);
    }

    private List<TaxPolicyBean> getPolicies() {
        return csvParser.getBeans("taxPolices.csv", TaxPolicyBean.class);
    }

    private int getDailyAmount() {
        return csvParser.getList("maxDailyAmount.csv").stream()
                .map(Integer::parseInt)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private List<VehicleType> getTollFreVehicleTypes() {
        return csvParser.getList("tollFreeVehicles.csv").stream()
                .map(VehicleType::valueOf)
                .collect(Collectors.toList());
    }

}
