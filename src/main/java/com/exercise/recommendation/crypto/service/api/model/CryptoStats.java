package com.exercise.recommendation.crypto.service.api.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CryptoStats {

    private CryptoPrice oldest;
    private CryptoPrice newest;
    private CryptoPrice min;
    private CryptoPrice max;

    public double getNormalizedRange() {
        return (getMax().getPrice() - getMin().getPrice()) / getMin().getPrice();
    }

}
