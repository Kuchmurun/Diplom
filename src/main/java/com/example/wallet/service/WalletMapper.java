package com.example.wallet.service;

import com.example.wallet.DTO.CurrencyDTO;
import com.example.wallet.DTO.WalletDTO;
import com.example.wallet.entity.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    @Mapping(target = "id", source = "id")
    WalletDTO toDTO(WalletEntity walletEntity);

    @Mapping(target = "id", source = "id")
    WalletEntity toEntity(WalletDTO dto);

    @Mapping(target = "id", source = "id")
    WalletEntity toEntityCurrency(CurrencyDTO currencyDTO);
}
