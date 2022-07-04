package com.exercise.recommendation.crypto.controller.api.model;

import com.exercise.recommendation.crypto.service.api.model.CryptoNormalizedRange;
import lombok.Getter;

@Getter
public class CryptoNormalizedRangeJso {

    private final String symbol;
    private final double normalizedRange;

    public CryptoNormalizedRangeJso(CryptoNormalizedRange cryptoNormalizedRange){
        symbol = cryptoNormalizedRange.getSymbol();
        normalizedRange = cryptoNormalizedRange.getNormalizedRange();
    }

}
