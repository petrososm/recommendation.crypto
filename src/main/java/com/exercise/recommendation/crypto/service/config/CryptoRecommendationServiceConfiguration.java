package com.exercise.recommendation.crypto.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@ApplicationScope
@Component
public class CryptoRecommendationServiceConfiguration {


    @Value("${accepted.cryptos}")
    private String acceptedCryptosStr;

    @Value("${accepted.cryptos.default.requiredDays}")
    private String acceptedCryptosDefaultRequiredDays;

    @Autowired
    private Environment env;
    private Set<AcceptedCryptoConfig> acceptedCryptos;

    @PostConstruct
    private void init() {
        acceptedCryptos = new HashSet<>();
        if (acceptedCryptosStr != null) {
            String[] split = acceptedCryptosStr.split(",");
            for (String symbol : split) {
                String requiredDays = env.getProperty("accepted.cryptos." + symbol + ".requiredDays", acceptedCryptosDefaultRequiredDays);
                acceptedCryptos.add(AcceptedCryptoConfig.builder()
                        .symbol(symbol)
                        .requiredMonths(Integer.parseInt(requiredDays)).
                        build());
            }
        }
    }


    public Set<AcceptedCryptoConfig> getAcceptedCryptos() {
        return acceptedCryptos;
    }
}
