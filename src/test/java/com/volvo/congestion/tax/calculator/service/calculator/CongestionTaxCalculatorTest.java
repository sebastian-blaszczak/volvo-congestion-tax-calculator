package com.volvo.congestion.tax.calculator.service.calculator;

import com.volvo.congestion.tax.calculator.domain.Tax;
import com.volvo.congestion.tax.calculator.repository.PolicyProvider;
import com.volvo.congestion.tax.calculator.service.utils.AssigmentPolicyContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.List;

import static com.volvo.congestion.tax.calculator.service.utils.TestDataUtils.toDate;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CongestionTaxCalculatorTest {

    private static CongestionTaxCalculatorImpl congestionTaxCalculator;

    @BeforeAll
    static void setUp() {
        PolicyProvider policyProvider = mock(PolicyProvider.class);
        when(policyProvider.getContext()).thenReturn(AssigmentPolicyContext.context());
        congestionTaxCalculator = new CongestionTaxCalculatorImpl(policyProvider);
    }

    @Test
    void shouldTakeOnlyHighestAmountWithinHour() {
        // given
        List<LocalDateTime> dates = List.of(
                toDate("2013-02-08T15:29:00"),
                toDate("2013-02-08T15:47:00"),
                toDate("2013-02-08T16:01:00")
        );

        // when
        Tax tax = congestionTaxCalculator.calculateTax(dates);

        // then
        Assertions.assertThat(tax.getValue()).isEqualTo(18);
    }

    @Test
    void shouldTakeHighestAmountWithinHourAndAlsoNextFee() {
        // given
        List<LocalDateTime> dates = List.of(
                toDate("2013-02-08T15:29:00"),
                toDate("2013-02-08T15:47:00"),
                toDate("2013-02-08T16:01:00"),
                toDate("2013-02-08T17:49:00")
        );

        // when
        Tax tax = congestionTaxCalculator.calculateTax(dates);

        // then
        Assertions.assertThat(tax.getValue()).isEqualTo(31);
    }

    @Test
    void shouldTakeCalculateTaxForOnePass() {
        // given
        List<LocalDateTime> dates = List.of(
                toDate("2013-02-08T15:29:00")
        );

        // when
        Tax tax = congestionTaxCalculator.calculateTax(dates);

        // then
        Assertions.assertThat(tax.getValue()).isEqualTo(13);
    }

    @Test
    void shouldProperlyCalculateTaxForWholeDay() {
        // given
        List<LocalDateTime> dates = List.of(
                toDate("2013-02-08T06:27:00"),
                toDate("2013-02-08T14:35:00"),
                toDate("2013-02-08T15:29:00"),
                toDate("2013-02-08T15:47:00"),
                toDate("2013-02-08T16:01:00"),
                toDate("2013-02-08T16:48:00"),
                toDate("2013-02-08T17:49:00"),
                toDate("2013-02-08T18:29:00"),
                toDate("2013-02-08T18:35:00")
        );

        // when
        Tax tax = congestionTaxCalculator.calculateTax(dates);

        // then
        Assertions.assertThat(tax.getValue()).isEqualTo(60);
    }

    @Test
    void shouldProperlyCalculateTaxForWholeDayAndOneTaxFromNextAnother() {
        // given
        List<LocalDateTime> dates = List.of(
                toDate("2013-02-08T06:27:00"),
                toDate("2013-02-08T14:35:00"),
                toDate("2013-02-08T15:29:00"),
                toDate("2013-02-08T15:47:00"),
                toDate("2013-02-08T16:01:00"),
                toDate("2013-02-08T16:48:00"),
                toDate("2013-02-08T17:49:00"),
                toDate("2013-02-08T18:29:00"),
                toDate("2013-02-08T18:35:00"),
                toDate("2013-03-26T14:25:00")
        );

        // when
        Tax tax = congestionTaxCalculator.calculateTax(dates);

        // then
        Assertions.assertThat(tax.getValue()).isEqualTo(68);
    }

    @Test
    void shouldNotCalculateTaxOnHoliday() {
        // given
        List<LocalDateTime> dates = List.of(
                toDate("2013-02-08T15:29:00")
        );

        // when
        Tax tax = congestionTaxCalculator.calculateTax(dates);

        // then
        Assertions.assertThat(tax.getValue()).isEqualTo(13);
    }

    @Test
    void shouldProperlyCalculateNightTax() {
        // given
        List<LocalDateTime> dates = List.of(
                toDate("2013-02-08T02:29:00")
        );

        // when
        Tax tax = congestionTaxCalculator.calculateTax(dates);

        // then
        Assertions.assertThat(tax.getValue()).isZero();
    }

    @ParameterizedTest
    @CsvSource(value = {"2013-01-01T12:29:00,0",
            "2013-03-27T12:29:00, 0",
            "2013-03-28T12:29:00, 0",
            "2013-03-29T12:29:00, 0",
            "2013-04-01T12:29:00, 0",
            "2013-04-29T12:29:00, 0",
            "2013-04-30T12:29:00, 0",
            "2013-05-01T12:29:00, 0",
            "2013-05-07T12:29:00, 0",
            "2013-05-08T12:29:00, 0",
            "2013-05-09T12:29:00, 0",
            "2013-06-04T12:29:00, 0",
            "2013-06-05T12:29:00, 0",
            "2013-06-07T12:29:00, 0",
            "2013-06-08T12:29:00, 0",
            "2013-06-20T12:29:00, 0",
            "2013-06-21T12:29:00, 0",
            "2013-07-01T12:29:00, 0",
            "2013-07-02T12:29:00, 0",
            "2013-11-01T12:29:00, 0",
            "2013-12-23T12:29:00, 0",
            "2013-12-24T12:29:00, 0",
            "2013-12-25T12:29:00, 0",
            "2013-12-26T12:29:00, 0",
    })
    void shouldProperlyCalculateFreeDay(String date, String taxAmount) {
        // given
        List<LocalDateTime> dates = List.of(
                toDate(date)
        );

        // when
        Tax tax = congestionTaxCalculator.calculateTax(dates);

        // then
        Assertions.assertThat(tax.getValue()).isEqualTo(Integer.valueOf(taxAmount));
    }
}