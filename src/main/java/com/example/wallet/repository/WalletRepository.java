package com.example.wallet.repository;

import com.example.wallet.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {


    Optional<WalletEntity> findById(Long id);

    @Query("select w.id from WalletEntity as w")
    List<Long> getAllId();

    @Query("select w.walletStatus from WalletEntity as w Where w.id = :id")
    String getWalletStatusById(@Param("id") Long id);

    @Modifying
    @Query("update WalletEntity w set w.walletStatus = 'AVAILABLE' where w.id = :id")
    Integer setAvailableStatus(@Param("id") Long id);


    @Modifying
    @Query("from WalletEntity w where w.isDeleted = false")
    List<WalletEntity> findNotDeleted();

    @Modifying
    @Query("from WalletEntity w where w.isDeleted = true")
    List<WalletEntity> findDeleted();

    @Transactional
    @Modifying
    @Query("update WalletEntity w set w.isDeleted = true where w.id = :id")
    Integer softDeleteById(@Param("id") Long id);


    @Query("select w.isDeleted from WalletEntity w where w.id = :id")
    Boolean ifSoftDeleted(Long id);
    // изменить
    // Пишем с тобой 3 разных метода: fillRub, fillUsd,fillEur
    // потом в сервисе уже управляем выбором метода из репозитория, то есть один метод
    // который принимает строку валюты от контроллера, и в соответствии с валютой
    // вызывает запрос в репозитории


//    @Modifying
//    @Query("update WalletEntity w set :currency = :currency + amount where w.id = id")
//    // UPDATE wallet SET currency = currency + amount WHERE w.id = id;
//    void fillBalance(@Param("id") Long id,
//                     @Param("amount") double amount,
//                     @Param("currency") String currency);

    @Modifying
    @Query("update WalletEntity w set w.rub = w.rub + :amount where w.id = :id")
    void fillRub(@Param("id") Long id,
                 @Param("amount") BigDecimal amount);

    @Modifying
    @Query("update WalletEntity w set w.usd = w.usd + :amount where w.id = :id")
    void fillUsd(@Param("id") Long id,
                 @Param("amount") BigDecimal amount);

    @Modifying
    @Query("update WalletEntity w set w.eur = w.eur + :amount where w.id = :id")
    void fillEur(@Param("id") Long id,
                 @Param("amount") BigDecimal amount);
}
