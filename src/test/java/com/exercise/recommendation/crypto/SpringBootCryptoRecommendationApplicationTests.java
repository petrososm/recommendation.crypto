package com.exercise.recommendation.crypto;

import com.exercise.recommendation.crypto.utils.csvparser.CsvParseException;
import com.exercise.recommendation.crypto.utils.csvparser.CsvReader;
import com.exercise.recommendation.crypto.dao.api.CryptoPriceRaw;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@SpringBootTest
class SpringBootCryptoRecommendationApplicationTests {

	@Autowired
	CsvReader csvReader;

	@Test
	void contextLoads() {
	}

	@Test
	void testCsvRead() throws FileNotFoundException, CsvParseException {
		File file = ResourceUtils.getFile("classpath:prices/BTC_values.csv");
		List<CryptoPriceRaw> cryptoPriceRaws = csvReader.readValues(new FileInputStream(file), CryptoPriceRaw.class);
		Assert.notEmpty(cryptoPriceRaws,"Failed to parse file correctly");
	}

}
