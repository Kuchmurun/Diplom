package com.example.bank.credit;

import com.example.bank.client.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreditScoringService {

    private final CreditScoreResultRepository scoreResultRepository;

    public CreditScoreResultEntity calculate(CreditApplicationEntity application) {
        ClientEntity client = application.getClient();

        int score = 50;

        if (client.getMonthlyIncome() != null) {
            int incomeScore = client.getMonthlyIncome()
                    .divide(BigDecimal.valueOf(10_000), 0, java.math.RoundingMode.DOWN)
                    .intValue();

            score += Math.min(incomeScore, 30);
        }

        if (client.getCreditHistoryScore() != null) {
            score += Math.min(client.getCreditHistoryScore() / 5, 20);
        }

        if (client.getCurrentCreditDebt() != null && client.getMonthlyIncome() != null) {
            BigDecimal debtLimit = client.getMonthlyIncome().multiply(BigDecimal.valueOf(6));

            if (client.getCurrentCreditDebt().compareTo(debtLimit) > 0) {
                score -= 25;
            } else {
                score -= 10;
            }
        }

        if (Boolean.TRUE.equals(client.getHasCriminalRecord())) {
            score -= 30;
        }

        if (score < 0) {
            score = 0;
        }

        if (score > 100) {
            score = 100;
        }

        CreditReliabilityLevel level = defineLevel(score);
        BigDecimal approvedAmount = calculateApprovedAmount(application, client, level);

        String reason = buildReason(score, level);

        CreditScoreResultEntity result = CreditScoreResultEntity.builder()
                .application(application)
                .score(score)
                .reliabilityLevel(level)
                .approvedAmount(approvedAmount)
                .decisionReason(reason)
                .calculatedAt(LocalDateTime.now())
                .build();

        return scoreResultRepository.save(result);
    }

    private CreditReliabilityLevel defineLevel(int score) {
        if (score >= 75) {
            return CreditReliabilityLevel.HIGH;
        }

        if (score >= 45) {
            return CreditReliabilityLevel.MEDIUM;
        }

        return CreditReliabilityLevel.LOW;
    }


    private BigDecimal calculateApprovedAmount(CreditApplicationEntity application,
                                               ClientEntity client,
                                               CreditReliabilityLevel level) {
        BigDecimal income = client.getMonthlyIncome() == null
                ? BigDecimal.ZERO
                : client.getMonthlyIncome();

        BigDecimal baseLimit = switch (level) {
            case HIGH -> income.multiply(BigDecimal.valueOf(12));
            case MEDIUM -> income.multiply(BigDecimal.valueOf(6));
            case LOW -> BigDecimal.ZERO;
        };

        if (application.getRequestedAmount() == null) {
            return baseLimit;
        }

        return baseLimit.min(application.getRequestedAmount());
    }

    private String buildReason(int score, CreditReliabilityLevel level) {
        if (level == CreditReliabilityLevel.HIGH) {
            return "Клиент имеет высокий уровень кредитной надежности. Заявка может быть одобрена автоматически.";
        }

        if (level == CreditReliabilityLevel.MEDIUM) {
            return "Клиент имеет средний уровень кредитной надежности. Требуется ручная проверка кредитным специалистом.";
        }

        return "Клиент имеет низкий уровень кредитной надежности. Выдача кредита не рекомендуется.";
    }
}
