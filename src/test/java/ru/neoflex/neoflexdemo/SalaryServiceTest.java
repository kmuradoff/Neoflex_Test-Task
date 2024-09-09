package ru.neoflex.neoflexdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.neoflexdemo.dto.SalaryRequest;
import ru.neoflex.neoflexdemo.dto.SalaryResponse;
import ru.neoflex.neoflexdemo.exceptions.UnprocessableEntityException;
import ru.neoflex.neoflexdemo.service.SalaryService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SalaryServiceTest {

    @InjectMocks
    private SalaryService salaryService;

    @Test
    public void testCalculateVacationPay_WithVacationDaysCount() {
        SalaryRequest salaryRequest = new SalaryRequest();
        salaryRequest.setMedianSalary(480000f);
        salaryRequest.setVacationDaysCount(17);

        SalaryResponse salaryResponse = salaryService.calculateVacationPay(salaryRequest);

        assertNotNull(salaryResponse);
        assertEquals("24401.91", salaryResponse.getVacationPay());
    }

    @Test
    public void testCalculateVacationPay_WithVacationDates() {
        SalaryRequest salaryRequest = new SalaryRequest();
        salaryRequest.setMedianSalary(480000f);

        Set<LocalDate> vacationDates = new HashSet<>();
        vacationDates.add(LocalDate.of(2024, 9, 9)); // Понедельник
        vacationDates.add(LocalDate.of(2024, 9, 10)); // Вторник
        vacationDates.add(LocalDate.of(2024, 9, 11)); // Среда
        vacationDates.add(LocalDate.of(2024, 9, 12)); // Четверг
        vacationDates.add(LocalDate.of(2024, 9, 13)); // Пятница
        vacationDates.add(LocalDate.of(2024, 9, 14)); // Суббота (Не должен считать, не рабочий день)

        salaryRequest.setVacationDates(vacationDates);

        SalaryResponse salaryResponse = salaryService.calculateVacationPay(salaryRequest);

        assertNotNull(salaryResponse);
        assertEquals("7177.03", salaryResponse.getVacationPay());
    }

    @Test
    public void testCalculateVacationPay_WithBothVacationDaysCountAndDates_ShouldThrowException() {
        SalaryRequest salaryRequest = new SalaryRequest();
        salaryRequest.setMedianSalary(120000f);
        salaryRequest.setVacationDaysCount(10);

        Set<LocalDate> vacationDates = new HashSet<>();
        vacationDates.add(LocalDate.of(2024, 9, 9));

        salaryRequest.setVacationDates(vacationDates);

        Exception exception = assertThrows(UnprocessableEntityException.class,
                () -> salaryService.calculateVacationPay(salaryRequest));

        assertEquals("Ошибка: Пожалуйста используйте только один способ для подсчета отпускных.", exception.getMessage());
    }
}
