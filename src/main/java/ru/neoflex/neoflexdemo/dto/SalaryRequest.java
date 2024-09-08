package ru.neoflex.neoflexdemo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SalaryRequest {
    private float medianSalary;
    private int vacationDaysCount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Set<LocalDate> vacationDates = new HashSet<>();
}
