package com.volvo.congestion.tax.calculator.service.calculator;

import com.volvo.congestion.tax.calculator.domain.Holiday;
import com.volvo.congestion.tax.calculator.domain.PolicyContext;
import com.volvo.congestion.tax.calculator.domain.Tax;
import com.volvo.congestion.tax.calculator.domain.TaxPolicy;
import com.volvo.congestion.tax.calculator.repository.PolicyProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CongestionTaxCalculatorImpl implements CongestionTaxCalculator {

    private final PolicyProvider policyProvider;

    public Tax calculateTax(List<LocalDateTime> dates) {
        List<LocalDateTime> sortedDates = new ArrayList<>(dates);
        sortedDates.sort(Comparator.naturalOrder());
        PolicyContext context = policyProvider.getContext();
        LocalDateTime hourlyStart = sortedDates.get(0);
        LocalDateTime dailyStart = sortedDates.get(0);
        int totalFee = 0;
        int dailyFee = 0;
        for (LocalDateTime date : sortedDates) {
            int nextFee = getTax(date, context);
            int tempFee = getTax(hourlyStart, context);

            long hours = ChronoUnit.HOURS.between(dailyStart, date);
            if (hours <= 24) {
                if (dailyFee > context.getMaxDailyAmount()) {
                    totalFee -= dailyFee;
                    totalFee += context.getMaxDailyAmount();
                }
            } else {
                dailyFee = 0;
                dailyStart = date;
            }

            long minutes = ChronoUnit.MINUTES.between(hourlyStart, date);
            if (minutes <= 60) {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
                dailyFee = totalFee;
                hourlyStart = date;
            } else {
                totalFee += nextFee;
                dailyFee = totalFee;
            }
        }
        return Tax.from(totalFee);
    }

    public int getTax(LocalDateTime date, PolicyContext context) {
        if (isFreeDay(date, context.getHolidays()))
            return 0;

        TaxPolicy policy = getPolicy(date, context.getTaxPolicies());
        return policy.getValue();
    }

    private boolean isFreeDay(LocalDateTime date, List<Holiday> holidays) {
        return isWeekend(date)
                || isHolyDay(date, holidays);
    }

    private boolean isWeekend(LocalDateTime date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private boolean isHolyDay(LocalDateTime date, List<Holiday> holidays) {
        return holidays.stream()
                .anyMatch(holiday -> holiday.isHolyDay(date));
    }

    private TaxPolicy getPolicy(LocalDateTime date, List<TaxPolicy> taxPolicies) {
        return taxPolicies.stream()
                .filter(policy -> policy.canBeApplied(date))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
