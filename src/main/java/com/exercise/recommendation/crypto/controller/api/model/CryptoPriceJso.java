package com.exercise.recommendation.crypto.controller.api.model;

import com.exercise.recommendation.crypto.service.api.model.CryptoPrice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CryptoPriceJso {
    private final LocalDateTime timestamp;
    private final double price;

    public CryptoPriceJso(CryptoPrice cryptoPrice) {
        timestamp = cryptoPrice.getTimestamp();
        price = cryptoPrice.getPrice();
    }
}
