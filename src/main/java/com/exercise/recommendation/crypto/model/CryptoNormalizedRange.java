package com.exercise.recommendation.crypto.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CryptoNormalizedRange {

    private String symbol;
    private double normalizedRange;
}
