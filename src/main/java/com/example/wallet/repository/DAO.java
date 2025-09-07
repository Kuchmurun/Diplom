package com.example.wallet.repository;

import com.example.wallet.entity.WalletEntity;

import java.util.List;

public interface DAO {
    void createWallet(WalletEntity walletEntity);

    List<WalletEntity> readWallet();

    void updateWallet(WalletEntity walletEntity);

    void fillBalance(int id, double amount, String currency);

    List<Integer> getAllId();

    WalletEntity readWalletById(int id);

    void deleteWallet(int id);
}
