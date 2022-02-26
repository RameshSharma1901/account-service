package com.jp.payments.restservice.util;


import com.jp.payments.restservice.config.AppConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class SourceApiUtils {

    private AppConfig appConfig;

    public String getApiUrlForGivenSourceName(String sourceName) {
        List<AppConfig.Provider> providers = appConfig.getProviders();

        Optional<AppConfig.Provider> optionalProvider = providers.stream().filter(provider -> provider.getName().equalsIgnoreCase(sourceName)).findFirst();

        return optionalProvider.isPresent() ? optionalProvider.get().getUrl() : "";
    }
}
