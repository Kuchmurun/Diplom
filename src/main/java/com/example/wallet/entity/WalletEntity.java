package com.example.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletEntity {
    private int id;
    private String first_name;
    private String last_name;
    private String middle_name;
    private int passport_id;
    private Double rub, usd, eur;

    private String walletStatus;

    public String getDefaultWalletStatus() {
        return "Доступен";
    }
}
