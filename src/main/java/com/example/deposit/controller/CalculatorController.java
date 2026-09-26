package com.example.deposit.controller;

import com.example.deposit.dto.CalculateRequest;
import com.example.deposit.dto.CalculateResponse;
import com.example.deposit.service.CalculatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    /**
     * Рассчитывает итоговую сумму вклада и доход по введённым параметрам.
     *
     * @param request сумма, срок в месяцах и годовая ставка
     * @return итоговая сумма и прибыль
     */
    @PostMapping("/calculate")
    public ResponseEntity<CalculateResponse> calculate(@Valid @RequestBody CalculateRequest request) {
        CalculateResponse response = calculatorService.calculate(request);
        return ResponseEntity.ok(response);
    }
}
