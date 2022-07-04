package com.exercise.recommendation.crypto;

import com.exercise.recommendation.crypto.dao.api.CryptoPricesDao;
import com.exercise.recommendation.crypto.exception.NotFoundException;
import com.exercise.recommendation.crypto.service.api.model.CryptoDayStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class CryptoDaoTest {

    @Autowired
    private CryptoPricesDao cryptoPricesDao;


    private static final String VALID_SYMBOL = "BTC";
    private static final String INVALID_SYMBOL = "BTC1";
    @Test
    void testGetCryptoPricesPositive(){
        List<CryptoDayStats> dailyStats = cryptoPricesDao.getDailyStats(VALID_SYMBOL);
        Assert.notEmpty(dailyStats,"Prices for BTC are not present in the db");
    }

    @Test
    void testGetCryptoPricesNegative(){
        boolean excThrown = false;
        try {
            List<CryptoDayStats> dailyStats = cryptoPricesDao.getDailyStats(INVALID_SYMBOL);
        }catch (NotFoundException nfe){
            //empty
            excThrown = true;
        }
        Assert.isTrue(excThrown,"Exception not thrown for incorrect symbol");
    }
}
