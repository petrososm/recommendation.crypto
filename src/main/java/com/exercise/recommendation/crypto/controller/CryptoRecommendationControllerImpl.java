package com.exercise.recommendation.crypto.controller;

import com.exercise.recommendation.crypto.controller.api.model.CryptoNormalizedRangeJso;
import com.exercise.recommendation.crypto.controller.api.CryptoRecommendationController;
import com.exercise.recommendation.crypto.controller.api.model.CryptoStatJso;
import com.exercise.recommendation.crypto.service.api.CryptoRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/crypto")
public class CryptoRecommendationControllerImpl implements CryptoRecommendationController {

    @Autowired
    private CryptoRecommendationService cryptoRecommendationService;

    @Override
    public List<CryptoNormalizedRangeJso> getNormalizedAll() {
        return cryptoRecommendationService.getNormalizedAll()
                .stream()
                .map(c->new CryptoNormalizedRangeJso(c))
                .collect(Collectors.toList());
    }

    @Override
    public CryptoNormalizedRangeJso getNormalizedBySymbol(String symbol) {
        return new CryptoNormalizedRangeJso(cryptoRecommendationService.getNormalizedBySymbol(symbol));
    }

    @Override
    public CryptoNormalizedRangeJso getMaxNormalizedByDate( LocalDate date) {
        return new CryptoNormalizedRangeJso(cryptoRecommendationService.getMaxNormalizedByDate(date));
    }


    @Override
    public CryptoStatJso getStatsBySymbol(String symbol) {
        return new CryptoStatJso(cryptoRecommendationService.getStatsBySymbol(symbol));
    }
}
