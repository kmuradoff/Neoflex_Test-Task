package ru.neoflex.neoflexdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationCalculationRequest {
    @NotNull
    @Positive
    private BigDecimal totalIncome;

    @Builder.Default
    private Set<LocalDate> vacationDates = new HashSet<>();

    @Min(1)
    @Max(60)
    private Integer vacationDays;
}