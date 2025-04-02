package ru.neoflex.neoflexdemo.service;

import java.time.LocalDate;

public interface HolidayService {
    boolean isHoliday(LocalDate date);
}