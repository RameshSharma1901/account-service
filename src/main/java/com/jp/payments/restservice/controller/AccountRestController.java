package com.jp.payments.restservice.controller;

import com.jp.payments.restservice.model.ApiRequest;
import com.jp.payments.restservice.model.ApiResponse;
import com.jp.payments.restservice.service.SourceApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
public class AccountRestController {
    private SourceApiService sourceApiService;

    @PostMapping(value = "/api/validate-account")
    public ApiResponse validateAccount(@RequestBody ApiRequest apiRequest){
        return sourceApiService.getResultFromSourceApi(apiRequest);
    }
}
