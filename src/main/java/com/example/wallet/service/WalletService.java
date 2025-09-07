package com.example.wallet.service;

import com.example.wallet.DTO.WalletDTO;
import com.example.wallet.entity.WalletEntity;
import com.example.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository repository;

    private final WalletMapper mapper;

    WalletService(WalletRepository repository, WalletMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void createWallet(WalletDTO dto) {
        WalletEntity walletEntity = mapper.toEntity(dto);
        repository.createWallet(walletEntity);
    }

    public void updateWallet(WalletDTO dto) {
        WalletEntity walletEntity = mapper.toEntity(dto);
        repository.updateWallet(walletEntity);
    }
}
