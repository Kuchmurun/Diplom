package com.example.bank.credit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditScoreResultRepository extends JpaRepository<CreditScoreResultEntity, Long> {

    Optional<CreditScoreResultEntity> findByApplicationId(Long applicationId);
}