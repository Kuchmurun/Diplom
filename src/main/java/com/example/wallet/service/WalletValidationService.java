package com.example.wallet.service;

import com.example.wallet.entity.WalletEntity;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.currency.CurrencyType;
import org.springframework.stereotype.Service;

@Service
public class WalletValidationService {

    private final WalletRepository repository;

    public WalletValidationService(WalletRepository repository) {
        this.repository = repository;
    }

    public boolean checkId(int id) {
        return repository.getAllId().contains(id);
    }

    public boolean checkAmount(WalletEntity walletEntity, double amount, CurrencyType from) {
        return from.get(walletEntity) >= amount;
    }
}
