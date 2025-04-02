package ru.neoflex.neoflexdemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.neoflex.neoflexdemo.config.NagerProperty;
import ru.neoflex.neoflexdemo.dto.PublicHoliday;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NagerHolidayServiceImpl implements HolidayService {
    private static final String PUBLIC_HOLIDAYS_PATH = "/PublicHolidays";
    private final NagerProperty nagerProperty;
    private final RestTemplate restTemplate;

    private final Set<LocalDate> cache = ConcurrentHashMap.newKeySet();

    @Cacheable(value = "holidays", key = "{#year, #nagerProperty.countryCode}")
    public List<LocalDate> getHolidays(int year) {
        String url = UriComponentsBuilder.fromHttpUrl(nagerProperty.getBaseUrl())
                .path(PUBLIC_HOLIDAYS_PATH)
                .pathSegment(String.valueOf(year), nagerProperty.getCountryCode())
                .toUriString();

        try {
            PublicHoliday[] response = restTemplate.getForObject(url, PublicHoliday[].class);
            assert response != null;
            return Arrays.stream(response)
                    .map(PublicHoliday::getDate)
                    .map(LocalDate::parse)
                    .peek(cache::add)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to fetch holidays from Nager.Date API", e);
            return null;
        }
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        if (cache.isEmpty()) {
            preloadCache(date.getYear());
        }
        return cache.contains(date);
    }

    private void preloadCache(int year) {
        getHolidays(year);
    }
}