package ru.neoflex.neoflexdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExclusionPeriod {
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}