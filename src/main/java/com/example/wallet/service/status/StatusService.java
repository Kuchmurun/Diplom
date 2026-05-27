package com.example.wallet.service.status;

import com.example.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatusService {
    WalletRepository repository;

    StatusService(WalletRepository repository) {
        this.repository = repository;
    }

    public String getForStatus(Status status) {
        String html = "";
        if (status.equals(Status.BLOCKED)) {
            html = "blocked";
        }
        if (status.equals(Status.DEACTIVATED)) {
            html = "deactivated";
        }
        if (status.equals(Status.UNCONFIRMED)) {
            html = "unconfirmed";
        }
        return html;
    }

    public void updateStatus(Long id) {
        repository.setAvailableStatus(id);
    }
}
