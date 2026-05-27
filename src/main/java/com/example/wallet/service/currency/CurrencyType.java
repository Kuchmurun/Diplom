package com.example.wallet.service.currency;

import com.example.wallet.entity.WalletEntity;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.Function;

public enum CurrencyType {
    RUB(WalletEntity::getRub, WalletEntity::setRub),
    USD(WalletEntity::getUsd, WalletEntity::setUsd),
    EUR(WalletEntity::getEur, WalletEntity::setEur);

    private final Function<WalletEntity, BigDecimal> getter;
    private final BiConsumer<WalletEntity, BigDecimal> setter;

    CurrencyType(Function<WalletEntity, BigDecimal> getter,
                 BiConsumer<WalletEntity, BigDecimal> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public BigDecimal get(WalletEntity walletEntity) {
        return getter.apply(walletEntity);
    }

    public void set(WalletEntity walletEntity, BigDecimal value) {
        setter.accept(walletEntity, value);
    }
}