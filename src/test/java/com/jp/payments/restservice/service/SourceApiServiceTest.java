package com.jp.payments.restservice.service;

import com.jp.payments.restservice.model.ApiRequest;
import com.jp.payments.restservice.model.ApiResponse;
import com.jp.payments.restservice.model.SourceRequest;
import com.jp.payments.restservice.model.SourceResponse;
import com.jp.payments.restservice.util.SourceApiUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SourceApiServiceTest {
    @Mock
    RestTemplate restTemplate;
    @Mock
    SourceApiUtils sourceApiUtils;
    @InjectMocks
    SourceApiService sourceApiService;
    ApiRequest apiRequest = new ApiRequest("A123456", Arrays.asList("source1","source2"));

    @Test
    public void restService(){
        Mockito.when(sourceApiUtils.getApiUrlForGivenSourceName("source1")).thenReturn("xyz");
        Mockito.when(sourceApiUtils.getApiUrlForGivenSourceName("source2")).thenReturn("abc");
        Mockito.when(restTemplate.postForEntity("xyz", new SourceRequest("A123456"), SourceResponse.class))
                .thenReturn(new ResponseEntity<>(new SourceResponse("source1", true), HttpStatus.OK));
        Mockito.when(restTemplate.postForEntity("abc", new SourceRequest("A123456"), SourceResponse.class))
                .thenReturn(new ResponseEntity<>(new SourceResponse("source2", false), HttpStatus.OK));
        ApiResponse apiResponse = sourceApiService.getResultFromSourceApi(apiRequest);

        assertNotNull(apiResponse);
    }

}