package com.jp.payments.restservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class SourceRequest {
    String accountNumber;
}
