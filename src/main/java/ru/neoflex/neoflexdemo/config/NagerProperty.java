package ru.neoflex.neoflexdemo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("holidays.nager")
public class NagerProperty {
    private String countryCode;
    private String baseUrl;
    private int connectTimeout;
    private int readTimeout;
}
