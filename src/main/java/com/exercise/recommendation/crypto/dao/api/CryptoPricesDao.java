package com.exercise.recommendation.crypto.dao.api;

import com.exercise.recommendation.crypto.service.api.model.CryptoDayStats;

import java.time.LocalDate;
import java.util.List;

public interface CryptoPricesDao {

    List<CryptoDayStats> getDailyStats(String cryptoName);
    CryptoDayStats getDailyStats(String cryptoName, LocalDate date);

}
