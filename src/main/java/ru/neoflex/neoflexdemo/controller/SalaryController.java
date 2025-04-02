package ru.neoflex.neoflexdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.neoflexdemo.dto.VacationCalculationRequest;
import ru.neoflex.neoflexdemo.service.VacationCalculationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SalaryController {
    private final VacationCalculationService calculationService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@Valid @RequestBody VacationCalculationRequest request) {
        return ResponseEntity.ok(calculationService.calculate(request));
    }
}
