package com.exercise.recommendation.crypto.controller.api.model;

import com.exercise.recommendation.crypto.service.api.model.CryptoStats;
import lombok.Getter;


@Getter
public class CryptoStatJso {
    private final CryptoPriceJso oldest;
    private final CryptoPriceJso newest;
    private final CryptoPriceJso min;
    private final CryptoPriceJso max;

    public CryptoStatJso(CryptoStats cryptoStats) {
        oldest = new CryptoPriceJso(cryptoStats.getOldest());
        newest = new CryptoPriceJso(cryptoStats.getNewest());
        min = new CryptoPriceJso(cryptoStats.getMin());
        max = new CryptoPriceJso(cryptoStats.getMax());
    }
}
