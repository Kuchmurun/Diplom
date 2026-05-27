package com.example.bank.client;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    List<ClientEntity> findByIsDeletedFalse();

    List<ClientEntity> findByFullNameAndIsDeletedFalse(String fullName);
}