package com.exercise.recommendation.crypto.service.config;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
public class CryptoRecommendationConfig {

    private String symbol;
    private int requiredMonths;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoRecommendationConfig that = (CryptoRecommendationConfig) o;
        return Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
