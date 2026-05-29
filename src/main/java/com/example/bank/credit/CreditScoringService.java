package com.example.bank.credit;

import com.example.bank.client.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreditScoringService {

    private final CreditScoreResultRepository scoreResultRepository;

    public CreditScoreResultEntity calculate(CreditApplicationEntity application) {
        ClientEntity client = application.getClient();

        int score = 50;
        StringBuilder reason = new StringBuilder();

        if (client.getMonthlyIncome() != null) {
            int incomeScore = client.getMonthlyIncome()
                    .divide(BigDecimal.valueOf(10_000), 0, RoundingMode.DOWN)
                    .intValue();

            int added = Math.min(incomeScore, 30);
            score += added;

            reason.append("Доход клиента добавил ")
                    .append(added)
                    .append(" баллов. ");
        } else {
            reason.append("Доход клиента не указан. ");
        }

        if (client.getCreditHistoryScore() != null) {
            int added = Math.min(client.getCreditHistoryScore() / 5, 20);
            score += added;

            reason.append("Кредитная история добавила ")
                    .append(added)
                    .append(" баллов. ");
        } else {
            reason.append("Кредитная история не указана. ");
        }

        if (client.getCurrentCreditDebt() != null && client.getMonthlyIncome() != null) {
            BigDecimal debtLimit = client.getMonthlyIncome().multiply(BigDecimal.valueOf(6));

            if (client.getCurrentCreditDebt().compareTo(debtLimit) > 0) {
                score -= 25;
                reason.append("Высокая долговая нагрузка снизила оценку на 25 баллов. ");
            } else {
                score -= 10;
                reason.append("Наличие текущей задолженности снизило оценку на 10 баллов. ");
            }
        }

        if (Boolean.TRUE.equals(client.getHasCriminalRecord())) {
            score -= 30;
            reason.append("Наличие судимости снизило оценку на 30 баллов. ");
        }

        score = Math.max(0, Math.min(score, 100));

        CreditReliabilityLevel level = defineLevel(score);
        BigDecimal approvedAmount = calculateApprovedAmount(application, client, level);

        reason.append("Итоговый балл: ")
                .append(score)
                .append(". Уровень надежности: ")
                .append(level)
                .append(".");

        CreditScoreResultEntity result = CreditScoreResultEntity.builder()
                .application(application)
                .score(score)
                .reliabilityLevel(level)
                .approvedAmount(approvedAmount)
                .decisionReason(reason.toString())
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
}