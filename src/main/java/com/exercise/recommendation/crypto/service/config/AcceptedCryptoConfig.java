package com.exercise.recommendation.crypto.service.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AcceptedCryptoConfig {

    private String symbol;
    private int requiredMonths;
}
