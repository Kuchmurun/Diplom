package com.example.bank.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<ClientEntity> findAllActive() {
        return clientRepository.findByIsDeletedFalse();
    }

    public ClientEntity findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Клиент не найден: " + id));
    }

    public ClientEntity save(ClientEntity client) {
        if (client.getIsDeleted() == null) {
            client.setIsDeleted(false);
        }

        return clientRepository.save(client);
    }

    public void softDelete(Long id) {
        ClientEntity client = findById(id);
        client.setIsDeleted(true);
        clientRepository.save(client);
    }

    public List<ClientEntity> searchByFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return findAllActive();
        }

        return clientRepository.findByFullNameAndIsDeletedFalse(fullName);
    }
}
