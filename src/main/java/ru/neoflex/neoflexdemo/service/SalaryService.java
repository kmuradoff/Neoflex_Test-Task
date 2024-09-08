package ru.neoflex.neoflexdemo.service;

import org.springframework.stereotype.Service;
import ru.neoflex.neoflexdemo.dto.SalaryRequest;
import ru.neoflex.neoflexdemo.dto.SalaryResponse;
import ru.neoflex.neoflexdemo.exceptions.UnprocessableEntityException;

import java.time.DayOfWeek;
import java.util.stream.Collectors;

@Service
public class SalaryService {
    public static final float WORKING_DAYS_IN_YEAR = 334.4f;

    public SalaryResponse calculateVacationPay(SalaryRequest salaryRequest) {
        float averageDailySalary = (salaryRequest.getMedianSalary() / WORKING_DAYS_IN_YEAR);
        SalaryResponse salaryResponse = new SalaryResponse();
        if (!salaryRequest.getVacationDates().isEmpty() && salaryRequest.getVacationDaysCount() > 0) {
            throw new UnprocessableEntityException("Ошибка: Пожалуйста используйте только один способ для подсчета отпускных.");
        }
        if (!salaryRequest.getVacationDates().isEmpty()) {
            salaryRequest.setVacationDates(salaryRequest.getVacationDates().stream()
                    .filter(date -> !(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY))
                    .collect(Collectors.toSet()));
            salaryRequest.setVacationDaysCount(salaryRequest.getVacationDates().size());
        }
        salaryResponse.setVacationPay(String.format("%.2f",
                averageDailySalary * salaryRequest.getVacationDaysCount()));
        return salaryResponse;
    }
}
