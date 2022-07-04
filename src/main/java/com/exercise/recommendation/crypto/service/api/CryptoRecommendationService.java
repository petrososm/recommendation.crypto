package com.exercise.recommendation.crypto.service.api;

import com.exercise.recommendation.crypto.service.api.model.CryptoNormalizedRange;
import com.exercise.recommendation.crypto.service.api.model.CryptoStats;

import java.time.LocalDate;
import java.util.List;

public interface CryptoRecommendationService {

    List<CryptoNormalizedRange> getNormalizedAll();

    CryptoNormalizedRange getNormalizedBySymbol(String symbol);

    CryptoNormalizedRange getMaxNormalizedByDate( LocalDate date);

    CryptoStats getStatsBySymbol(String symbol);

}
