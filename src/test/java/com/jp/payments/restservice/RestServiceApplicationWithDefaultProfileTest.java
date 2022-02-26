package com.jp.payments.restservice;

import com.jp.payments.restservice.config.AppConfig;
import com.jp.payments.restservice.util.SourceApiUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestServiceApplicationWithDefaultProfileTest {

	@Autowired
	AppConfig appConfig;

	@Autowired
	SourceApiUtils sourceApiUtils;

	@Test
	void contextLoads() {
		Assertions.assertEquals(2, appConfig.getProviders().size());
		Assertions.assertEquals("https://source2.com/v1/api/account/validate", sourceApiUtils.getApiUrlForGivenSourceName("source2"));
		Assertions.assertEquals("https://source1.com/v1/api/account/validate", sourceApiUtils.getApiUrlForGivenSourceName("source1"));
		Assertions.assertEquals("", sourceApiUtils.getApiUrlForGivenSourceName("source3"));
	}

}
