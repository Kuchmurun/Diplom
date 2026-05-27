package com.example.wallet.service;

import com.example.wallet.entity.WalletEntity;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.currency.CurrencyType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletValidationService {

    private final WalletRepository repository;

    public WalletValidationService(WalletRepository repository) {
        this.repository = repository;
    }

    public boolean checkId(Long id) {
        return repository.getAllId().contains(id);
    }

    public boolean checkAmount(WalletEntity walletEntity, BigDecimal amount, CurrencyType from) {
        return from.get(walletEntity).compareTo(amount) >= 0;
    }
}
