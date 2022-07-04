package com.exercise.recommendation.crypto.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CryptoPrice {

    private LocalDateTime timestamp;
    private double price;

}
