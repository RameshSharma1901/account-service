package com.jp.payments.restservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
@ConfigurationProperties
public class AppConfig {
    private List<Provider> providers;

    @Data
    public static class Provider {
        private String name;
        private String url;
    }
}
