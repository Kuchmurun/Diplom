package com.example.bank.credit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditApplicationRepository extends JpaRepository<CreditApplicationEntity, Long> {

    List<CreditApplicationEntity> findByClientId(Long clientId);
}