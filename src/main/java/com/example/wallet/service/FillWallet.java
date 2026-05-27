package com.example.wallet.service;

import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.currency.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class FillWallet {
    // DTO
    @Autowired
    WalletRepository repo;

    public void fill(Long id, BigDecimal amount, String currency) {
        try {
            CurrencyType type = CurrencyType.valueOf(currency.toUpperCase());
            if (type == CurrencyType.RUB) {
                repo.fillRub(id, amount);
            }
            if (type == CurrencyType.USD) {
                repo.fillUsd(id, amount);
            }
            if (type == CurrencyType.EUR) {
                repo.fillEur(id, amount);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Недопустимая валюта.");
        }
    }
}
