package com.exercise.recommendation.crypto.dao.api;

import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CryptoPriceRaw {

    @CsvBindByName
    private Long timestamp;
    @CsvBindByName
    private String symbol;
    @CsvBindByName
    private double price;

    public CryptoPriceRaw() {
    }

    public CryptoPriceRaw(Long timestamp, String symbol, double price) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
    }
}
