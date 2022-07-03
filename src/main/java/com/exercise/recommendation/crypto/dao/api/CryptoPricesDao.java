package com.exercise.recommendation.crypto.dao.api;

import com.exercise.recommendation.crypto.model.CryptoStats;

import java.time.LocalDate;

public interface CryptoPricesDao {

    CryptoStats getStats(String cryptoName);
    CryptoStats getDayStats(String cryptoName, LocalDate date);

}
