package com.example.bank.credit;

import com.example.bank.client.ClientEntity;
import com.example.bank.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditApplicationService {

    private final CreditApplicationRepository applicationRepository;
    private final ClientService clientService;
    private final CreditScoringService scoringService;

    public List<CreditApplicationEntity> findAll() {
        return applicationRepository.findAll();
    }

    public CreditApplicationEntity findById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка не найдена: " + id));
    }

    public CreditApplicationEntity create(Long clientId, BigDecimal requestedAmount, Integer requestedMonths) {
        ClientEntity client = clientService.findById(clientId);

        CreditApplicationEntity application = CreditApplicationEntity.builder()
                .client(client)
                .requestedAmount(requestedAmount)
                .requestedMonths(requestedMonths)
                .status(CreditApplicationStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        return applicationRepository.save(application);
    }

    public CreditScoreResultEntity calculateScore(Long applicationId) {
        CreditApplicationEntity application = findById(applicationId);

        CreditScoreResultEntity result = scoringService.calculate(application);

        if (result.getReliabilityLevel() == CreditReliabilityLevel.HIGH) {
            application.setStatus(CreditApplicationStatus.APPROVED);
        } else if (result.getReliabilityLevel() == CreditReliabilityLevel.MEDIUM) {
            application.setStatus(CreditApplicationStatus.MANUAL_REVIEW);
        } else {
            application.setStatus(CreditApplicationStatus.REJECTED);
        }

        applicationRepository.save(application);

        return result;
    }
}