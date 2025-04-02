package ru.neoflex.neoflexdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.neoflexdemo.dto.VacationCalculationRequest;
import ru.neoflex.neoflexdemo.dto.VacationCalculationResponse;
import ru.neoflex.neoflexdemo.exception.InvalidRequestException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;

@Service
@RequiredArgsConstructor
public class VacationCalculationService {
    private static final BigDecimal AVERAGE_CALENDAR_DAYS_PER_MONTH = BigDecimal.valueOf(29.3);
    private static final int CALCULATION_SCALE = 10;
    private static final int RESULT_SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final HolidayService holidayService;

    public VacationCalculationResponse calculate(VacationCalculationRequest request) {
        validateRequest(request);

        BigDecimal totalIncome = request.getTotalIncome();
        BigDecimal totalWorkingDays = calculateTotalWorkingDays(request);

        BigDecimal averageDailyEarnings = totalIncome.divide(totalWorkingDays, CALCULATION_SCALE, ROUNDING_MODE);
        int vacationDays = calculateEffectiveVacationDays(request);

        BigDecimal vacationPay = averageDailyEarnings.multiply(BigDecimal.valueOf(vacationDays))
                .setScale(RESULT_SCALE, ROUNDING_MODE);

        return new VacationCalculationResponse(vacationPay);
    }

    private void validateRequest(VacationCalculationRequest request) {
        if (request.getTotalIncome().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Total income must be positive");
        }

        if (request.getVacationDates().isEmpty() && request.getVacationDays() <= 0) {
            throw new InvalidRequestException("Must specify either vacation dates or vacation days");
        }
    }

    private BigDecimal calculateTotalWorkingDays(VacationCalculationRequest request) {
        BigDecimal totalDays = BigDecimal.ZERO;
        totalDays = totalDays.add(AVERAGE_CALENDAR_DAYS_PER_MONTH);

        return totalDays;
    }

    private int calculateEffectiveVacationDays(VacationCalculationRequest request) {
        if (!request.getVacationDates().isEmpty()) {
            return (int) request.getVacationDates().stream()
                    .filter(date -> !holidayService.isHoliday(date))
                    .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY)
                    .filter(date -> date.getDayOfWeek() != DayOfWeek.SUNDAY)
                    .count();
        }
        return request.getVacationDays();
    }
}

