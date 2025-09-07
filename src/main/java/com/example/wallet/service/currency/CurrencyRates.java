package com.example.wallet.service.currency;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyRates {
    static final Map<Map.Entry<CurrencyType, CurrencyType>, Double> rates = new HashMap<>();

    static {
        rates.put(Map.entry(CurrencyType.RUB, CurrencyType.USD), 0.012);
        rates.put(Map.entry(CurrencyType.RUB, CurrencyType.EUR), 0.011);
        rates.put(Map.entry(CurrencyType.USD, CurrencyType.RUB), 80.55);
        rates.put(Map.entry(CurrencyType.USD, CurrencyType.EUR), 0.86);
        rates.put(Map.entry(CurrencyType.EUR, CurrencyType.RUB), 93.94);
        rates.put(Map.entry(CurrencyType.EUR, CurrencyType.USD), 1.17);
    }

    public static double getRate(CurrencyType from, CurrencyType to) {
        return rates.getOrDefault(Map.entry(from, to), 0.0);
    }
}
