package com.example.deposit.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Входные параметры расчёта вклада.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CalculateRequest {

    @NotNull(message = "Поле amount обязательно для заполнения")
    @DecimalMin(value = "1000", message = "Сумма вклада не может быть меньше 1000")
    @DecimalMax(value = "10000000", message = "Сумма вклада не может превышать 10 000 000")
    private BigDecimal amount;

    @NotNull(message = "Поле months обязательно для заполнения")
    @Min(value = 1, message = "Срок вклада не может быть меньше 1 месяца")
    @Max(value = 60, message = "Срок вклада не может превышать 60 месяцев")
    private Integer months;

    @NotNull(message = "Поле rate обязательно для заполнения")
    @DecimalMin(value = "1.0", message = "Ставка не может быть меньше 1%")
    @DecimalMax(value = "20.0", message = "Ставка не может превышать 20%")
    private BigDecimal rate;
}
