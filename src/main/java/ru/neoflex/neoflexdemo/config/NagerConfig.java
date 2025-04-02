package ru.neoflex.neoflexdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class NagerConfig {
    private final NagerProperty nagerProperty;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(nagerProperty.getConnectTimeout()))
                .setReadTimeout(Duration.ofMillis(nagerProperty.getReadTimeout()))
                .build();
    }
}
