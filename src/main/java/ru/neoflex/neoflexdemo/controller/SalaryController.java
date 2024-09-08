package ru.neoflex.neoflexdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.neoflexdemo.dto.SalaryRequest;
import ru.neoflex.neoflexdemo.dto.SalaryResponse;
import ru.neoflex.neoflexdemo.service.SalaryService;

@RestController
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @GetMapping("/calculate")
    private SalaryResponse calculate(SalaryRequest salaryRequest) {
        return salaryService.calculateVacationPay(salaryRequest);
    }
}
