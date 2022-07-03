package com.exercise.recommendation.crypto.controller.api;

import com.exercise.recommendation.crypto.model.CryptoNormalizedRange;
import com.exercise.recommendation.crypto.model.CryptoStats;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/crypto")
public interface CryptoRecommendationController {


    @GetMapping("normalized")
    List<CryptoNormalizedRange> getNormalized();

    @GetMapping("normalized/{name}")
    CryptoNormalizedRange getNormalized(@PathVariable String name);

    @GetMapping("normalized/{name}/date/{date}")
    CryptoNormalizedRange getNormalizedForDate(@PathVariable String name,
                                               @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);


    @GetMapping("stats/{name}")
    CryptoStats getStats(@PathVariable String name);


}
