package com.example.wallet.service.currency;

import com.example.wallet.entity.WalletEntity;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum CurrencyType {
    RUB(WalletEntity::getRub, WalletEntity::setRub),
    USD(WalletEntity::getUsd, WalletEntity::setUsd),
    EUR(WalletEntity::getEur, WalletEntity::setEur);

    private final Function<WalletEntity, Double> getter;
    private final BiConsumer<WalletEntity, Double> setter;

    CurrencyType(Function<WalletEntity, Double> getter,
                 BiConsumer<WalletEntity, Double> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public double get(WalletEntity walletEntity) {
        return getter.apply(walletEntity);
    }

    public void set(WalletEntity walletEntity, double value) {
        setter.accept(walletEntity, value);
    }
}
