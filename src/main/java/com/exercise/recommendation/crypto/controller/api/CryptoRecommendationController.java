package com.exercise.recommendation.crypto.controller.api;

import com.exercise.recommendation.crypto.controller.api.model.CryptoNormalizedRangeJso;
import com.exercise.recommendation.crypto.controller.api.model.CryptoStatJso;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

public interface CryptoRecommendationController {



    @GetMapping("normalized")
    List<CryptoNormalizedRangeJso> getNormalizedAll();

    @GetMapping("normalized/{symbol}")
    CryptoNormalizedRangeJso getNormalizedBySymbol(@PathVariable String symbol);

    @GetMapping("normalized/max/date/{date}")
    CryptoNormalizedRangeJso getMaxNormalizedByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @GetMapping("stats/{symbol}")
    CryptoStatJso getStatsBySymbol(@PathVariable String symbol);


}
