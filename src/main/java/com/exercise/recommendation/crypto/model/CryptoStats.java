package com.exercise.recommendation.crypto.model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CryptoStats {

    private String symbol;
    private CryptoPrice oldest;
    private CryptoPrice newest;
    private CryptoPrice min;
    private CryptoPrice max;

    public double getNormalizedRange() {
        return (getMax().getPrice() - getMin().getPrice()) / getMin().getPrice();
    }

}
