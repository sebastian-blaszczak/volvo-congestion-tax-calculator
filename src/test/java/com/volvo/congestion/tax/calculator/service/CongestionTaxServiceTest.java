package com.volvo.congestion.tax.calculator.service;

import com.volvo.congestion.tax.calculator.domain.VehicleType;
import com.volvo.congestion.tax.calculator.dto.TaxDto;
import com.volvo.congestion.tax.calculator.repository.PolicyProvider;
import com.volvo.congestion.tax.calculator.service.calculator.CongestionTaxCalculator;
import com.volvo.congestion.tax.calculator.service.calculator.CongestionTaxCalculatorImpl;
import com.volvo.congestion.tax.calculator.service.command.CongestionTaxCommand;
import com.volvo.congestion.tax.calculator.service.utils.AssigmentPolicyContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.List;

import static com.volvo.congestion.tax.calculator.service.utils.TestDataUtils.toDate;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CongestionTaxServiceTest {

    private CongestionTaxProvider taxProvider;

    @BeforeEach
    void setup() {
        PolicyProvider policyProvider = mock(PolicyProvider.class);
        when(policyProvider.getContext()).thenReturn(AssigmentPolicyContext.context());
        CongestionTaxCalculator calculator = new CongestionTaxCalculatorImpl(policyProvider);
        taxProvider = new CongestionTaxService(calculator, policyProvider);
    }

    @ParameterizedTest
    @CsvSource(value = {"CAR,13",
            "TRUCK, 13",
            "EMERGENCY_VEHICLE,0",
            "BUS,0",
            "DIPLOMAT_VEHICLE,0",
            "MOTORCYCLE,0",
            "MILITARY_VEHICLE,0",
            "FOREIGN_VEHICLE,0"})
    void shouldProperlyCalculateTaxBasedOnType(String providedVehicleType, String taxAmount) {
        // given
        VehicleType vehicleType = VehicleType.valueOf(providedVehicleType);
        List<LocalDateTime> dates = List.of(
                toDate("2013-02-08T15:29:00")
        );

        // when
        TaxDto tax = taxProvider.getTax(CongestionTaxCommand.builder()
                .dates(dates)
                .vehicleType(vehicleType)
                .build());

        // then
        Assertions.assertThat(tax.getValue()).isEqualTo(Integer.valueOf(taxAmount));
    }
}