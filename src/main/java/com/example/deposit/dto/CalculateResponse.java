package com.example.deposit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Результат расчёта вклада.
 *
 * total - итоговая сумма вклада на конец срока
 * profit - доход (разница между итоговой и начальной суммой)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CalculateResponse {

    private BigDecimal total;

    private BigDecimal profit;
}
