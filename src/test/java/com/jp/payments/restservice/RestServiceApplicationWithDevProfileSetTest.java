package com.jp.payments.restservice;

import com.jp.payments.restservice.config.AppConfig;
import com.jp.payments.restservice.util.SourceApiUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class RestServiceApplicationWithDevProfileSetTest {
    @Autowired
    AppConfig appConfig;

    @Autowired
    SourceApiUtils sourceApiUtils;

    @Test
    void contextLoads() {
        Assertions.assertEquals(2, appConfig.getProviders().size());
        Assertions.assertEquals("https://source3.com/v1/api/account/validate", sourceApiUtils.getApiUrlForGivenSourceName("source3"));
        Assertions.assertEquals("https://source4.com/v1/api/account/validate", sourceApiUtils.getApiUrlForGivenSourceName("source4"));
        Assertions.assertEquals("", sourceApiUtils.getApiUrlForGivenSourceName("source5"));
    }
}