package com.exercise.recommendation.crypto.dao;

import com.exercise.recommendation.crypto.dao.api.*;
import com.exercise.recommendation.crypto.model.CryptoPrice;
import com.exercise.recommendation.crypto.model.CryptoStats;
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


@ApplicationScope
@Component
public class CryptoPricesDaoFileImpl implements CryptoPricesDao {

    private final Logger LOGGER = LoggerFactory.getLogger(CryptoPricesDaoFileImpl.class);

    @Autowired
    private CsvReader csvReader;


    private Map<String, List<CryptoPriceRaw>> cryptoPricesDb;

    private Map<String, Map<LocalDate, CryptoStats>> dayStatsMap;
    private Map<String, CryptoStats> allStatsMap;


    @Value("${crypto.resource.folder}")
    private String csvPath = "prices";


    @PostConstruct
    private void init() throws CsvParseException, IOException {
        cryptoPricesDb = new HashMap<>();
        dayStatsMap = new HashMap<>();
        allStatsMap = new HashMap<>();
        try {
            Resource[] resources = FileUtils.getResources(csvPath, this.getClass().getClassLoader());
            for (Resource cryptoPricesFileName : resources) {
                String cryptoName = cryptoPricesFileName.getFilename().replace("_values.csv", "");

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


                Map<LocalDate, CryptoStats> cryptoDayStats = new LinkedHashMap();

                CryptoPrice newestValue = pricesPerDay.entrySet().iterator().next().getValue().get(0);
                CryptoPrice oldestValue = newestValue;
                CryptoPrice max = newestValue;
                CryptoPrice min = newestValue;
                for (Map.Entry<LocalDate, List<CryptoPrice>> entry : pricesPerDay.entrySet()) {
                    LocalDate k = entry.getKey();
                    List<CryptoPrice> v = entry.getValue();
                    oldestValue = v.get(v.size() - 1);
                    CryptoStats cryptoDayStat = computeCryptoDayStats(v);
                    cryptoDayStats.put(k,cryptoDayStat);
                    if(max.getPrice()<cryptoDayStat.getMax().getPrice()){
                        max = cryptoDayStat.getMax();
                    }
                    if(min.getPrice()>cryptoDayStat.getMin().getPrice()){
                        min = cryptoDayStat.getMin();
                    }
                }
                CryptoStats cryptoStats = CryptoStats.builder().newest(newestValue).oldest(oldestValue).min(min).max(max).build();


                cryptoPricesDb.computeIfAbsent(cryptoName, c -> new ArrayList<>()).addAll(cryptoPriceRaws);
                dayStatsMap.computeIfAbsent(cryptoName, c -> new HashMap<>()).putAll(cryptoDayStats);
                allStatsMap.put(cryptoName,cryptoStats);

            }

        } catch (Exception e) {
            LOGGER.error("Unable to initialize crypto prices database");
            throw e;
        }
    }

    private CryptoStats computeCryptoDayStats(List<CryptoPrice> v) {
        CryptoPrice max = v.get(0);
        CryptoPrice min = v.get(0);
        for (CryptoPrice cryptoPrice : v) {
            if (cryptoPrice.getPrice() > max.getPrice()) {
                max = cryptoPrice;
            } else if (cryptoPrice.getPrice() < min.getPrice()) {
                min = cryptoPrice;
            }
        }

        CryptoStats cryptoDayStat = CryptoStats.builder()
                .newest(v.get(0))
                .oldest(v.get(v.size() - 1))
                .max(max)
                .min(min)
                .build();
        return cryptoDayStat;
    }


    @Override
    public CryptoStats getStats(String cryptoName) {
        return allStatsMap.get(cryptoName);
    }

    @Override
    public CryptoStats getDayStats(String cryptoName, LocalDate date) {
        Map<LocalDate, CryptoStats> localDateCryptoStatsMap = dayStatsMap.get(cryptoName);
        //assert not null
        return localDateCryptoStatsMap.get(date);
    }
}
