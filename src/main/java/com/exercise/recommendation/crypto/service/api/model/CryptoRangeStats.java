package com.exercise.recommendation.crypto.service.api.model;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
public class CryptoRangeStats extends CryptoStats {

    private LocalDate from;
    private LocalDate to;


    @Builder(builderMethodName = "cryptoStatsBuilder")
    public CryptoRangeStats(CryptoPrice oldest, CryptoPrice newest, CryptoPrice min, CryptoPrice max, LocalDate from, LocalDate to) {
        super(oldest, newest, min, max);
        this.from = from;
        this.to = to;
    }

    public long getDaysRange() {
        return ChronoUnit.DAYS.between(getFrom(), getTo())+1;
    }
}
