package com.example.wallet.service.currency;

import com.example.wallet.entity.WalletEntity;
import com.example.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrencyExchange {
    @Autowired
    private WalletRepository repo;
    @Autowired
    private JdbcTemplate template;

    public WalletEntity currencyExchange(CurrencyType fromCurrency, CurrencyType toCurrency, int id, double amount) {
        WalletEntity walletEntity = repo.readWalletById(id);
        double balance = fromCurrency.get(walletEntity);
        double rate = CurrencyRates.getRate(fromCurrency, toCurrency);
        fromCurrency.set(walletEntity, balance - amount);
        toCurrency.set(walletEntity, toCurrency.get(walletEntity) + amount * rate);
        return walletEntity;
    }

    @Transactional
    public void currencyTransaction(CurrencyType fromCurrency, CurrencyType toCurrency, int id, double amount) {
        WalletEntity walletEntity = currencyExchange(fromCurrency, toCurrency, id, amount);
        String sql = "UPDATE wallets SET " + fromCurrency + " = ?," + toCurrency + " = ? WHERE id = ?";
        template.update(sql, fromCurrency.get(walletEntity), toCurrency.get(walletEntity), id);
        System.out.println("-----SQL-----");
        System.out.println(sql);
        System.out.println(fromCurrency.get(walletEntity));
        System.out.println(toCurrency.get(walletEntity));
        System.out.println(id);
    }
}
