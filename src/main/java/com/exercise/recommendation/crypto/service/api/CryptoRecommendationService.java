package com.exercise.recommendation.crypto.service.api;

import com.exercise.recommendation.crypto.model.CryptoNormalizedRange;
import com.exercise.recommendation.crypto.model.CryptoStats;

import java.time.LocalDate;
import java.util.List;

public interface CryptoRecommendationService {

    List<CryptoNormalizedRange> getCryptoNormalizedRanges();

    CryptoNormalizedRange getCryptoNormalizedRange(String name);

    CryptoNormalizedRange getCryptoNormalizedRangeForDate(String name, LocalDate date);

    CryptoStats getStats(String name);

}
