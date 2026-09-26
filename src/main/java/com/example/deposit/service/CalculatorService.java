package com.example.deposit.service;

import com.example.deposit.dto.CalculateRequest;
import com.example.deposit.dto.CalculateResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Сервис расчёта вклада.
 */
@Service
public class CalculatorService {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal TWELVE = BigDecimal.valueOf(12);

    private static final MathContext CALC_CONTEXT = new MathContext(10, RoundingMode.HALF_UP);

    private static final int RESULT_SCALE = 2;
    private static final RoundingMode RESULT_ROUNDING = RoundingMode.HALF_UP;

    public CalculateResponse calculate(CalculateRequest request) {
        BigDecimal amount = request.getAmount();
        BigDecimal compoundedGrowth = getCompoundedGrowth(request);

        BigDecimal total = amount
                .multiply(compoundedGrowth, CALC_CONTEXT)
                .setScale(RESULT_SCALE, RESULT_ROUNDING);

        BigDecimal profit = total.subtract(amount.setScale(RESULT_SCALE, RESULT_ROUNDING));

        return new CalculateResponse(total, profit);
    }

    private static BigDecimal getCompoundedGrowth(CalculateRequest request) {
        BigDecimal annualRate = request.getRate();
        int months = request.getMonths();

        BigDecimal monthlyRate = annualRate
                .divide(ONE_HUNDRED, CALC_CONTEXT)
                .divide(TWELVE, CALC_CONTEXT);

        BigDecimal growthFactor = BigDecimal.ONE.add(monthlyRate, CALC_CONTEXT);

        return growthFactor.pow(months, CALC_CONTEXT);
    }
}
