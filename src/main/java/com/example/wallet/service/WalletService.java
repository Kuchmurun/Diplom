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
        walletEntity.setWalletStatus(walletEntity.getDefaultWalletStatus());
        System.out.println("----------------TEST----------------");
        System.out.println(dto.toString());
        System.out.println(walletEntity.toString());
        repository.save(walletEntity);
    }

    public void updateWallet(WalletDTO dto) {
        WalletEntity walletEntity = mapper.toEntity(dto);
        walletEntity.setWalletStatus(walletEntity.getDefaultWalletStatus());
        repository.save(walletEntity);
    }
}
