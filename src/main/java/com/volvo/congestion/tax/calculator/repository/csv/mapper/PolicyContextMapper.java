package com.volvo.congestion.tax.calculator.repository.csv.mapper;

import com.volvo.congestion.tax.calculator.domain.Holiday;
import com.volvo.congestion.tax.calculator.domain.PolicyContext;
import com.volvo.congestion.tax.calculator.domain.TaxPolicy;
import com.volvo.congestion.tax.calculator.domain.VehicleType;
import com.volvo.congestion.tax.calculator.repository.csv.beans.HolyDayBean;
import com.volvo.congestion.tax.calculator.repository.csv.beans.TaxPolicyBean;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PolicyContextMapper {

    public static PolicyContext toContext(List<HolyDayBean> holidays, List<TaxPolicyBean> policies, int maxDailyAmount,
                                          List<VehicleType> tollFreeVehicleTypes) {
        return new PolicyContext(
                tollFreeVehicleTypes,
                getPolicies(policies),
                getHolidays(holidays),
                maxDailyAmount
        );
    }

    private static List<Holiday> getHolidays(List<HolyDayBean> holidays) {
        return holidays.stream()
                .map(PolicyContextMapper::toHoliday)
                .collect(Collectors.toList());
    }

    private static List<TaxPolicy> getPolicies(List<TaxPolicyBean> policies) {
        return policies.stream()
                .map(PolicyContextMapper::toPolicy)
                .collect(Collectors.toList());
    }

    private static Holiday toHoliday(HolyDayBean holiday) {
        return Holiday.from(Month.valueOf(holiday.getMonth()), holiday.getDayOfMonth(), holiday.isWholeMonth());
    }

    private static TaxPolicy toPolicy(TaxPolicyBean taxPolicyBean) {
        return TaxPolicy.from(LocalTime.of(taxPolicyBean.getHourFrom(), taxPolicyBean.getMinutesFrom()),
                LocalTime.of(taxPolicyBean.getHourTo(), taxPolicyBean.getMinutesTo()),
                taxPolicyBean.getTax());
    }
}
