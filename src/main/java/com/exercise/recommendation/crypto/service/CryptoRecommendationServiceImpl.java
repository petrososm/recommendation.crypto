package com.exercise.recommendation.crypto.service;

import com.exercise.recommendation.crypto.dao.api.CryptoPricesDao;
import com.exercise.recommendation.crypto.model.CryptoStats;
import com.exercise.recommendation.crypto.model.CryptoNormalizedRange;
import com.exercise.recommendation.crypto.service.api.CryptoRecommendationService;
import com.exercise.recommendation.crypto.service.config.AcceptedCryptoConfig;
import com.exercise.recommendation.crypto.service.config.CryptoRecommendationServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoRecommendationServiceImpl implements CryptoRecommendationService {

    @Autowired
    private CryptoPricesDao cryptoPricesDao;


    @Autowired
    private CryptoRecommendationServiceConfiguration configuration;

    @Override
    public List<CryptoNormalizedRange> getCryptoNormalizedRanges() {
        return configuration.getAcceptedCryptos().stream()
                .map(c -> {
                    CryptoStats stats = cryptoPricesDao.getStats(c.getSymbol());
                    return CryptoNormalizedRange.builder().symbol(c.getSymbol()).normalizedRange(stats.getNormalizedRange()).build();
                }).sorted(Comparator.comparing(CryptoNormalizedRange::getNormalizedRange)
                        .reversed())
                .collect(Collectors.toList());
    }

    @Override
    public CryptoNormalizedRange getCryptoNormalizedRange(String name) {
        if (configuration.getAcceptedCryptos()
                .stream()
                .map(AcceptedCryptoConfig::getSymbol)
                .collect(Collectors.toSet()).contains(name)) {
            CryptoStats stats = cryptoPricesDao.getStats(name);
            return CryptoNormalizedRange.builder().symbol(name).normalizedRange(stats.getNormalizedRange()).build();
        }//todo: exception
        return null;
    }

    @Override
    public CryptoNormalizedRange getCryptoNormalizedRangeForDate(String name, LocalDate date) {
        if (configuration.getAcceptedCryptos()
                .stream()
                .map(AcceptedCryptoConfig::getSymbol)
                .collect(Collectors.toSet()).contains(name)) {
            CryptoStats stats = cryptoPricesDao.getDayStats(name,date);
            return CryptoNormalizedRange.builder().symbol(name).normalizedRange(stats.getNormalizedRange()).build();
        }//todo: exception
        return null;
    }

    @Override
    public CryptoStats getStats(String name) {
        if (configuration.getAcceptedCryptos()
                .stream()
                .map(AcceptedCryptoConfig::getSymbol)
                .collect(Collectors.toSet()).contains(name)) {
            CryptoStats stats = cryptoPricesDao.getStats(name);
            return stats;
        }//todo: exception
        return null;
    }
}
