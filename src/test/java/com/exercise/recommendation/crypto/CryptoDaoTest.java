package com.exercise.recommendation.crypto;

import com.exercise.recommendation.crypto.dao.api.CryptoPriceRaw;
import com.exercise.recommendation.crypto.dao.api.CryptoPricesDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class CryptoDaoTest {

    @Autowired
    private CryptoPricesDao cryptoPricesDao;

    @Test
    void testGetCryptoPricesPositive(){
        List<CryptoPriceRaw> btc = cryptoPricesDao.getCryptoPrices("BTC");
        Assert.notEmpty(btc,"Prices for BTC are not present in the db");
    }

    @Test
    void testGetCryptoPricesNegative(){
        List<CryptoPriceRaw> btc = cryptoPricesDao.getCryptoPrices("BTC_inv");
        Assert.isTrue(btc.size()==0,"Prices for BTC_inv are present in the db");
    }
}
