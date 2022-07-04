package com.exercise.recommendation.crypto;

import com.exercise.recommendation.crypto.utils.csvparser.CsvParseException;
import com.exercise.recommendation.crypto.utils.csvparser.CsvReader;
import com.exercise.recommendation.crypto.dao.api.model.CryptoPriceRaw;
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


	@Test
	void contextLoads() {
	}



}
