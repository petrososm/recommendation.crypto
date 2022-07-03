package com.exercise.recommendation.crypto.controller;

import com.exercise.recommendation.crypto.controller.api.CryptoRecommendationController;
import com.exercise.recommendation.crypto.model.CryptoNormalizedRange;
import com.exercise.recommendation.crypto.model.CryptoStats;
import com.exercise.recommendation.crypto.service.api.CryptoRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
public class CryptoRecommendationControllerImpl implements CryptoRecommendationController {

    @Autowired
    private CryptoRecommendationService cryptoRecommendationService;

    @Override
    public List<CryptoNormalizedRange> getNormalized() {
        return cryptoRecommendationService.getCryptoNormalizedRanges();
    }

    @Override
    public CryptoNormalizedRange getNormalized(String name) {
        return cryptoRecommendationService.getCryptoNormalizedRange(name);
    }

    @Override
    public CryptoNormalizedRange getNormalizedForDate(String name, LocalDate date) {
        return cryptoRecommendationService.getCryptoNormalizedRangeForDate(name,date);
    }


    @Override
    public CryptoStats getStats(String name) {
        return cryptoRecommendationService.getStats(name);
    }
}
