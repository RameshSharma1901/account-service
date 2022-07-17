package com.jp.payments.restservice.service;

import com.jp.payments.restservice.model.ApiRequest;
import com.jp.payments.restservice.model.ApiResponse;
import com.jp.payments.restservice.model.SourceRequest;
import com.jp.payments.restservice.model.SourceResponse;
import com.jp.payments.restservice.util.SourceApiUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 */
@AllArgsConstructor
@Service
public class SourceApiService {
    SourceApiUtils sourceApiUtils;
    RestTemplate restTemplate;

    /**
     * @param apiRequest
     * @return
     */
    public ApiResponse getResultFromSourceApi(ApiRequest apiRequest){
        String accountNumber = apiRequest.getAccountNumber();
        List<String> sources = apiRequest.getSources();
        List<SourceResponse> sourceResponseList = sources.stream().map(source -> callSourceApi(accountNumber, source).getBody()).collect(Collectors.toList());
        return new ApiResponse(sourceResponseList);
    }

    private ResponseEntity<SourceResponse> callSourceApi(String accountNumber, String source) {
        return restTemplate.postForEntity(sourceApiUtils.getApiUrlForGivenSourceName(source)
                , new SourceRequest(accountNumber)
                , SourceResponse.class);
    }
}
