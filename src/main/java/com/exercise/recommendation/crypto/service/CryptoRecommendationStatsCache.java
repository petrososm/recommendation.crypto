package com.exercise.recommendation.crypto.service;


import com.exercise.recommendation.crypto.dao.api.CryptoPricesDao;
import com.exercise.recommendation.crypto.exception.NotFoundException;
import com.exercise.recommendation.crypto.service.api.model.CryptoDayStats;
import com.exercise.recommendation.crypto.service.api.model.CryptoPrice;
import com.exercise.recommendation.crypto.service.api.model.CryptoRangeStats;
import com.exercise.recommendation.crypto.service.api.model.CryptoStats;
import com.exercise.recommendation.crypto.service.config.CryptoRecommendationServiceConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScope
@Component
public class CryptoRecommendationStatsCache {


    private Map<String, CryptoRangeStats> cachedStats;

    protected void refreshCache(CryptoPricesDao cryptoPricesDao, CryptoRecommendationServiceConfiguration configuration) {
        cachedStats = new ConcurrentHashMap<>();
        for (String symbol : configuration.getAcceptedCryptoSymbols()) {
            List<CryptoDayStats> statsPerDay = cryptoPricesDao.getDailyStats(symbol);
            CryptoDayStats firstDay = statsPerDay.get(0);
            CryptoDayStats lastDay = statsPerDay.get(statsPerDay.size() - 1);

            CryptoPrice newestValue = firstDay.getNewest();
            CryptoPrice max = firstDay.getMax();
            CryptoPrice min = firstDay.getMin();
            LocalDate from = firstDay.getDay();

            LocalDate to = lastDay.getDay();
            CryptoPrice oldestValue = lastDay.getOldest();


            for (CryptoStats cryptoStats : statsPerDay) {
                if (max.getPrice() < cryptoStats.getMax().getPrice()) {
                    max = cryptoStats.getMax();
                }
                if (min.getPrice() > cryptoStats.getMin().getPrice()) {
                    min = cryptoStats.getMin();
                }
            }
            CryptoRangeStats cryptoStats = CryptoRangeStats.cryptoStatsBuilder()
                    .from(from)
                    .to(to)
                    .newest(newestValue)
                    .oldest(oldestValue)
                    .min(min)
                    .max(max)
                    .build();
            cachedStats.put(symbol, cryptoStats);
        }
    }

    public CryptoRangeStats getStatsForSymbol(String cryptoName) {
        CryptoRangeStats cryptoRangeStats = cachedStats.get(cryptoName);
        if (cryptoRangeStats == null){
            throw new NotFoundException("No data exist for symbol "+ cryptoName);
        }
        return cryptoRangeStats;
    }
}
