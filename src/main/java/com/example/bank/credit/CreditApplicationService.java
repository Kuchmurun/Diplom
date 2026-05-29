package com.example.bank.credit;

import com.example.bank.audit.AuditService;
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
    private final AuditService auditService;

    public List<CreditApplicationEntity> findAll() {
        return applicationRepository.findAll();
    }

    public CreditApplicationEntity findById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка не найдена: " + id));
    }

    public CreditApplicationEntity create(Long clientId, BigDecimal requestedAmount, Integer requestedMonths) {
        ClientEntity client = clientService.findById(clientId);
        validateCreditApplication(client, requestedAmount, requestedMonths);


        CreditApplicationEntity application = CreditApplicationEntity.builder()
                .client(client)
                .requestedAmount(requestedAmount)
                .requestedMonths(requestedMonths)
                .status(CreditApplicationStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        CreditApplicationEntity savedApplication = applicationRepository.save(application);

        auditService.log(
                "CREATE_CREDIT_APPLICATION",
                "CreditApplication",
                savedApplication.getId(),
                "Создана кредитная заявка для клиента: " + client.getFullName()
        );

        return savedApplication;
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

        auditService.log(
                "CALCULATE_CREDIT_SCORE",
                "CreditApplication",
                application.getId(),
                "Выполнен расчёт кредитной надёжности. Балл: "
                        + result.getScore()
                        + ", уровень: "
                        + result.getReliabilityLevel()
        );

        return result;
    }

    private void validateCreditApplication(ClientEntity client,
                                           BigDecimal requestedAmount,
                                           Integer requestedMonths) {
        if (requestedAmount == null || requestedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма кредита должна быть больше 0");
        }

        if (requestedMonths == null || requestedMonths < 3 || requestedMonths > 60) {
            throw new IllegalArgumentException("Срок кредита должен быть от 3 до 60 месяцев");
        }

        BigDecimal monthlyIncome = client.getMonthlyIncome() == null
                ? BigDecimal.ZERO
                : client.getMonthlyIncome();

        BigDecimal absoluteLimit = monthlyIncome.multiply(BigDecimal.valueOf(12));

        BigDecimal monthlyPayment = requestedAmount.divide(
                BigDecimal.valueOf(requestedMonths),
                2,
                java.math.RoundingMode.HALF_UP
        );

        BigDecimal maxMonthlyPayment = monthlyIncome.multiply(BigDecimal.valueOf(0.4));

        if (requestedAmount.compareTo(absoluteLimit) > 0) {
            throw new IllegalArgumentException(
                    "Запрошенная сумма превышает допустимый лимит по доходу клиента"
            );
        }

        if (monthlyPayment.compareTo(maxMonthlyPayment) > 0) {
            throw new IllegalArgumentException(
                    "Ежемесячный платёж превышает 40% дохода клиента"
            );
        }


    }
}