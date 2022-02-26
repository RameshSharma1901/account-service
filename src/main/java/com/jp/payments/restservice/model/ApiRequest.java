package com.jp.payments.restservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ApiRequest {
    private String accountNumber;
    private List<String> sources;
}
