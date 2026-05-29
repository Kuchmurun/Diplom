package com.example.bank.client;

import com.example.bank.audit.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final AuditService auditService;

    public List<ClientEntity> findAllActive() {
        return clientRepository.findByIsDeletedFalse();
    }

    public ClientEntity findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Клиент не найден: " + id));
    }

    public ClientEntity save(ClientEntity client) {
        boolean isNew = client.getId() == null;

        if (client.getIsDeleted() == null) {
            client.setIsDeleted(false);
        }

        ClientEntity savedClient = clientRepository.save(client);

        if (isNew) {
            auditService.log(
                    "CREATE_CLIENT",
                    "Client",
                    savedClient.getId(),
                    "Создан клиент: " + savedClient.getFullName()
            );
        } else {
            auditService.log(
                    "UPDATE_CLIENT",
                    "Client",
                    savedClient.getId(),
                    "Изменены данные клиента: " + savedClient.getFullName()
            );
        }

        return savedClient;
    }

    public void softDelete(Long id) {
        ClientEntity client = findById(id);
        client.setIsDeleted(true);
        clientRepository.save(client);

        auditService.log(
                "DELETE_CLIENT",
                "Client",
                client.getId(),
                "Клиент помечен как удалённый: " + client.getFullName()
        );
    }

    public List<ClientEntity> searchByFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return findAllActive();
        }

        return clientRepository.findByFullNameAndIsDeletedFalse(fullName);
    }
}
