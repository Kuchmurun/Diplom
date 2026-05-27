package com.example.wallet.service.currency;

import com.example.wallet.entity.WalletEntity;
import com.example.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CurrencyExchange {
    @Autowired
    private WalletRepository repo;
    @Autowired
    private JdbcTemplate template;

    @Transactional
    public void currencyExchange(CurrencyType fromCurrency, CurrencyType toCurrency, Long id, BigDecimal amount) {
        WalletEntity walletEntity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Кошелёк не найден"));
        System.out.println("1: " + walletEntity);
        BigDecimal balance = fromCurrency.get(walletEntity);
        System.out.println("2: " + walletEntity);
        BigDecimal rate = CurrencyRates.getRate(fromCurrency, toCurrency);
        System.out.println("3: " + walletEntity);
        fromCurrency.set(walletEntity, balance.subtract(amount)); // Отъем валюты
        System.out.println("4: " + walletEntity);
        toCurrency.set(walletEntity, toCurrency.get(walletEntity).add(amount.multiply(rate))); // Копейки добавились
        System.out.println("5: " + walletEntity);
        repo.save(walletEntity);
        System.out.println("6: " + walletEntity);
    }

//    @Transactional
//    public void currencyTransaction(CurrencyType fromCurrency, CurrencyType toCurrency, Long id, BigDecimal amount) {
//        WalletEntity walletEntity = currencyExchange(fromCurrency, toCurrency, id, amount);
//        String sql = "UPDATE wallets SET " + fromCurrency + " = ?," + toCurrency + " = ? WHERE id = ?";
//        template.update(sql, fromCurrency.get(walletEntity), toCurrency.get(walletEntity), id);
//        System.out.println("-----SQL-----");
//        System.out.println(sql);
//        System.out.println(fromCurrency.get(walletEntity));
//        System.out.println(toCurrency.get(walletEntity));
//        System.out.println(id);
//    }
}
