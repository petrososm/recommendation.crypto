package com.exercise.recommendation.crypto.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class CryptoPriceDto {

    private LocalDateTime timestamp;
    private String symbol;
    private double price;

    public CryptoPriceDto(LocalDateTime timestamp, String symbol, double price) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
    }
}
