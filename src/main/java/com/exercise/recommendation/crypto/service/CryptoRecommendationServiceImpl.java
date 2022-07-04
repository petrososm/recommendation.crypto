package com.exercise.recommendation.crypto.service;

import com.exercise.recommendation.crypto.dao.api.CryptoPricesDao;
import com.exercise.recommendation.crypto.exception.NotAcceptableException;
import com.exercise.recommendation.crypto.exception.NotFoundException;
import com.exercise.recommendation.crypto.service.api.model.CryptoDayStats;
import com.exercise.recommendation.crypto.service.api.model.CryptoRangeStats;
import com.exercise.recommendation.crypto.service.api.model.CryptoStats;
import com.exercise.recommendation.crypto.service.api.model.CryptoNormalizedRange;
import com.exercise.recommendation.crypto.service.api.CryptoRecommendationService;
import com.exercise.recommendation.crypto.service.config.CryptoRecommendationServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CryptoRecommendationServiceImpl implements CryptoRecommendationService {

    @Autowired
    private CryptoPricesDao cryptoPricesDao;

    @Autowired
    private CryptoRecommendationServiceConfiguration configuration;

    @Autowired
    private CryptoRecommendationStatsCache cryptoRecommendationStatsCache;

    @PostConstruct
    private void init() {
        refreshCache();
    }

    @Override
    public List<CryptoNormalizedRange> getNormalizedAll() {
        return configuration.getAcceptedCryptoSymbols()
                .stream()
                .map(this::getNormalizedRangeForSymbol)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(CryptoNormalizedRange::getNormalizedRange).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public CryptoNormalizedRange getNormalizedBySymbol(String symbol) {
        assertSupportedSymbol(symbol);
        CryptoNormalizedRange normalizedRangeForSymbol = getNormalizedRangeForSymbol(symbol);
        if (normalizedRangeForSymbol == null) {
            throw new RuntimeException("Not enough days for recommendation");
        }
        return normalizedRangeForSymbol;
    }

    @Override
    public CryptoNormalizedRange getMaxNormalizedByDate(LocalDate date) {
        return configuration.getAcceptedCryptoSymbols().stream()
                .map(symbol -> {
                    CryptoDayStats dailyStats = cryptoPricesDao.getDailyStats(symbol, date);
                    return Optional.ofNullable(dailyStats)
                            .map(d -> CryptoNormalizedRange.builder()
                                    .symbol(symbol)
                                    .normalizedRange(d.getNormalizedRange())
                                    .build())
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .max(Comparator.comparing(CryptoNormalizedRange::getNormalizedRange))
                .orElseThrow(() -> new NotFoundException("No crypto found for selected day"));
    }

    @Override
    public CryptoStats getStatsBySymbol(String symbol) {
        assertSupportedSymbol(symbol);
        return cryptoRecommendationStatsCache.getStatsForSymbol(symbol);
    }

    private void refreshCache() {
        cryptoRecommendationStatsCache.refreshCache(cryptoPricesDao, configuration);
    }

    private CryptoNormalizedRange getNormalizedRangeForSymbol(String symbol) {
        CryptoRangeStats rs = cryptoRecommendationStatsCache.getStatsForSymbol(symbol);
        if (configuration.getRequiredDays(symbol) <= rs.getDaysRange()) {
            return CryptoNormalizedRange
                    .builder()
                    .symbol(symbol)
                    .normalizedRange(rs.getNormalizedRange())
                    .build();
        }
        return null;
    }

    private void assertSupportedSymbol(String symbol) {
        if (!configuration.isCryptoSupported(symbol)) {
            throw new NotAcceptableException("Not Supported Symbol " + symbol);
        }
    }


}
