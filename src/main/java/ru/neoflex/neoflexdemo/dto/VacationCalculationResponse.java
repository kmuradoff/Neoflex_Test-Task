package ru.neoflex.neoflexdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class VacationCalculationResponse {
    private final BigDecimal vacationPay;
}