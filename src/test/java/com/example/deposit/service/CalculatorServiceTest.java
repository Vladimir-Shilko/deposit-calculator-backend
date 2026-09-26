package com.example.deposit.service;

import com.example.deposit.dto.CalculateRequest;
import com.example.deposit.dto.CalculateResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class CalculatorServiceTest {

    private final CalculatorService service = new CalculatorService();

    @Test
    void calculatesKnownExample_100000_12months_8_5percent() {
        CalculateRequest request = new CalculateRequest(
                BigDecimal.valueOf(100000),
                12,
                BigDecimal.valueOf(8.5)
        );

        CalculateResponse response = service.calculate(request);

        assertThat(response.getTotal().doubleValue()).isCloseTo(108839.09, offset(1.0));
        assertThat(response.getProfit().doubleValue()).isCloseTo(8839.09, offset(1.0));
    }

    @Test
    void calculatesTestExample_100000_12months_8percent() {
        CalculateRequest request = new CalculateRequest(
                BigDecimal.valueOf(100000),
                12,
                BigDecimal.valueOf(8)
        );

        CalculateResponse response = service.calculate(request);

        assertThat(response.getTotal().doubleValue()).isCloseTo(108300.0, offset(50.0));
        assertThat(response.getProfit().doubleValue()).isCloseTo(8300.0, offset(50.0));
    }

    @Test
    void profitEqualsTotalMinusAmount() {
        CalculateRequest request = new CalculateRequest(
                BigDecimal.valueOf(50000),
                24,
                BigDecimal.valueOf(12.0)
        );

        CalculateResponse response = service.calculate(request);

        BigDecimal expectedProfit = response.getTotal().subtract(BigDecimal.valueOf(50000).setScale(2));
        assertThat(response.getProfit()).isEqualByComparingTo(expectedProfit);
    }

    @Test
    void oneMonthDeposit_totalIsGreaterThanAmount() {
        CalculateRequest request = new CalculateRequest(
                BigDecimal.valueOf(1000),
                1,
                BigDecimal.valueOf(1.0)
        );

        CalculateResponse response = service.calculate(request);

        assertThat(response.getTotal()).isGreaterThan(BigDecimal.valueOf(1000));
    }
}
