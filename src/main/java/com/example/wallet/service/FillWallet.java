package com.example.wallet.service;

import com.example.wallet.entity.WalletEntity;
import org.springframework.stereotype.Service;

@Service
public class FillWallet {

    public double fill(WalletEntity walletEntity, double amount, String currency) {
        double sum = 0;
        if (currency.equals("rub")) {
            sum = walletEntity.getRub() + amount;
        } else if (currency.equals("usd")) {
            sum = walletEntity.getUsd() + amount;
        } else if (currency.equals("eur")) {
            sum = walletEntity.getEur() + amount;
        }
        return sum;
    }
}
