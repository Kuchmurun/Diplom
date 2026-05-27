package com.example.wallet.service.currency;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyRates {
    static final Map<Map.Entry<CurrencyType, CurrencyType>, BigDecimal> rates = new HashMap<>();

    static {
        rates.put(Map.entry(CurrencyType.RUB, CurrencyType.USD), BigDecimal.valueOf(0.012));
        rates.put(Map.entry(CurrencyType.RUB, CurrencyType.EUR), BigDecimal.valueOf(0.011));
        rates.put(Map.entry(CurrencyType.USD, CurrencyType.RUB), BigDecimal.valueOf(80.55));
        rates.put(Map.entry(CurrencyType.USD, CurrencyType.EUR), BigDecimal.valueOf(0.86));
        rates.put(Map.entry(CurrencyType.EUR, CurrencyType.RUB), BigDecimal.valueOf(93.94));
        rates.put(Map.entry(CurrencyType.EUR, CurrencyType.USD), BigDecimal.valueOf(1.17));
    }

    public static BigDecimal getRate(CurrencyType from, CurrencyType to) {
        return rates.getOrDefault(Map.entry(from, to), BigDecimal.valueOf(0.0));
    }
}
