package com.exercise.recommendation.crypto.dao;

import com.exercise.recommendation.crypto.dao.api.*;
import com.exercise.recommendation.crypto.dao.api.model.CryptoPriceRaw;
import com.exercise.recommendation.crypto.exception.NotFoundException;
import com.exercise.recommendation.crypto.service.api.model.CryptoDayStats;
import com.exercise.recommendation.crypto.service.api.model.CryptoPrice;
import com.exercise.recommendation.crypto.utils.csvparser.CsvParseException;
import com.exercise.recommendation.crypto.utils.csvparser.CsvReader;
import com.exercise.recommendation.crypto.utils.date.DateUtils;
import com.exercise.recommendation.crypto.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@ApplicationScope
@Component
public class CryptoPricesDaoFileImpl implements CryptoPricesDao {

    private final Logger LOGGER = LoggerFactory.getLogger(CryptoPricesDaoFileImpl.class);

    @Autowired
    private CsvReader csvReader;


    private Map<String, List<CryptoPriceRaw>> cryptoPricesDb;

    private Map<String, Map<LocalDate, CryptoDayStats>> dayStatsMap;


    @Value("${crypto.resource.folder}")
    private String csvPath;

    @Value("${crypto.resource.filePostfix}")
    private String filePostfix;


    @PostConstruct
    private void init() throws CsvParseException, IOException {
        cryptoPricesDb = new HashMap<>();
        dayStatsMap = new HashMap<>();
        try {
            Resource[] resources = FileUtils.getResourcesFromClasspath(csvPath, this.getClass().getClassLoader());
            for (Resource cryptoPricesFileName : resources) {
                String cryptoName = cryptoPricesFileName.getFilename().replace(filePostfix, "");

                List<CryptoPriceRaw> cryptoPriceRaws = csvReader.readValues(
                        cryptoPricesFileName.getInputStream(),
                        CryptoPriceRaw.class);

                cryptoPriceRaws.sort(Comparator.comparing(CryptoPriceRaw::getTimestamp));
                Map<LocalDate, List<CryptoPrice>> pricesPerDay = new LinkedHashMap<>();
                for (CryptoPriceRaw cryptoPriceRaw : cryptoPriceRaws) {
                    LocalDateTime localDateTime = DateUtils.parseDateFromTimeStamp(cryptoPriceRaw.getTimestamp());
                    LocalDate localDate = localDateTime.toLocalDate();
                    CryptoPrice cryptoPrice = new CryptoPrice(localDateTime, cryptoPriceRaw.getPrice());
                    pricesPerDay.computeIfAbsent(localDate, p -> new ArrayList<>()).add(cryptoPrice);
                }


                Map<LocalDate, CryptoDayStats> cryptoDayStats = new LinkedHashMap<>();
                pricesPerDay.forEach((day, priceList) -> {
                    CryptoDayStats cryptoDayStat = computeCryptoDayStats(day, priceList);
                    cryptoDayStats.put(day, cryptoDayStat);
                });

                cryptoPricesDb.computeIfAbsent(cryptoName, c -> new ArrayList<>()).addAll(cryptoPriceRaws);
                dayStatsMap.computeIfAbsent(cryptoName, c -> new HashMap<>()).putAll(cryptoDayStats);

            }

        } catch (Exception e) {
            LOGGER.error("Unable to initialize crypto prices database");
            throw e;
        }
    }

    private CryptoDayStats computeCryptoDayStats(LocalDate day, List<CryptoPrice> v) {
        CryptoPrice max = v.get(0);
        CryptoPrice min = v.get(0);
        for (CryptoPrice cryptoPrice : v) {
            if (cryptoPrice.getPrice() > max.getPrice()) {
                max = cryptoPrice;
            } else if (cryptoPrice.getPrice() < min.getPrice()) {
                min = cryptoPrice;
            }
        }
        return CryptoDayStats.cryptoStatsBuilder()
                .newest(v.get(0))
                .oldest(v.get(v.size() - 1))
                .max(max)
                .min(min)
                .day(day)
                .build();
    }


    @Override
    public List<CryptoDayStats> getDailyStats(String cryptoName) {
        Map<LocalDate, CryptoDayStats> dayStatsMap = this.dayStatsMap.get(cryptoName);
        if (dayStatsMap == null) {
            throw new NotFoundException("No data exist for symbol " + cryptoName);
        }
        return dayStatsMap.values().stream().sorted(Comparator.comparing(CryptoDayStats::getDay)).collect(Collectors.toList());
    }

    @Override
    public CryptoDayStats getDailyStats(String cryptoName, LocalDate date) {
        Map<LocalDate, CryptoDayStats> localDateCryptoStatsMap = dayStatsMap.get(cryptoName);
        if (localDateCryptoStatsMap == null) {
            throw new NotFoundException("No data exist for symbol " + cryptoName);
        }
        return localDateCryptoStatsMap.get(date);
    }
}
