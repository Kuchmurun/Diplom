package com.example.wallet.service.deleteService;

import com.example.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SoftDelete {

    private final WalletRepository repository;

    SoftDelete(WalletRepository repository) {
        this.repository = repository;
    }

    public Boolean softChecker(Long id) {
        return repository.ifSoftDeleted(id);
    }

    public String getSoftPage() {
        return "isDeleted";
    }

    public void deleteById(Long id) {
        repository.softDeleteById(id);
    }
}
