package com.exercise.recommendation.crypto.service.api.model;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CryptoDayStats extends CryptoStats {

    private LocalDate day;

    @Builder(builderMethodName = "cryptoStatsBuilder")
    public CryptoDayStats(CryptoPrice oldest, CryptoPrice newest, CryptoPrice min, CryptoPrice max, LocalDate day) {
        super(oldest, newest, min, max);
        this.day = day;
    }
}
