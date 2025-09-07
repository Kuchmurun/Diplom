package com.example.wallet.service;

import com.example.wallet.DTO.CurrencyDTO;
import com.example.wallet.DTO.WalletDTO;
import com.example.wallet.entity.WalletEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T15:57:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class WalletMapperImpl implements WalletMapper {

    @Override
    public WalletDTO toDTO(WalletEntity walletEntity) {
        if ( walletEntity == null ) {
            return null;
        }

        WalletDTO walletDTO = new WalletDTO();

        walletDTO.setId( walletEntity.getId() );
        walletDTO.setFirst_name( walletEntity.getFirst_name() );
        walletDTO.setLast_name( walletEntity.getLast_name() );
        walletDTO.setMiddle_name( walletEntity.getMiddle_name() );
        walletDTO.setPassport_id( walletEntity.getPassport_id() );
        walletDTO.setRub( walletEntity.getRub() );
        walletDTO.setUsd( walletEntity.getUsd() );
        walletDTO.setEur( walletEntity.getEur() );
        walletDTO.setWalletStatus( walletEntity.getWalletStatus() );

        return walletDTO;
    }

    @Override
    public WalletEntity toEntity(WalletDTO dto) {
        if ( dto == null ) {
            return null;
        }

        WalletEntity walletEntity = new WalletEntity();

        walletEntity.setId( dto.getId() );
        walletEntity.setFirst_name( dto.getFirst_name() );
        walletEntity.setLast_name( dto.getLast_name() );
        walletEntity.setMiddle_name( dto.getMiddle_name() );
        walletEntity.setPassport_id( dto.getPassport_id() );
        walletEntity.setRub( dto.getRub() );
        walletEntity.setUsd( dto.getUsd() );
        walletEntity.setEur( dto.getEur() );
        walletEntity.setWalletStatus( dto.getWalletStatus() );

        return walletEntity;
    }

    @Override
    public WalletEntity toEntityCurrency(CurrencyDTO currencyDTO) {
        if ( currencyDTO == null ) {
            return null;
        }

        WalletEntity walletEntity = new WalletEntity();

        walletEntity.setId( currencyDTO.getId() );

        return walletEntity;
    }
}
