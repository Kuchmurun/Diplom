package com.example.bank.report;

import com.example.bank.credit.CreditApplicationRepository;
import com.example.bank.credit.CreditApplicationStatus;
import com.example.bank.credit.CreditScoreResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CreditApplicationRepository applicationRepository;
    private final CreditScoreResultRepository scoreResultRepository;

    public long countApplications() {
        return applicationRepository.count();
    }

    public long countApprovedApplications() {
        return applicationRepository.countByStatus(CreditApplicationStatus.APPROVED);
    }

    public long countRejectedApplications() {
        return applicationRepository.countByStatus(CreditApplicationStatus.REJECTED);
    }

    public long countManualReviewApplications() {
        return applicationRepository.countByStatus(CreditApplicationStatus.MANUAL_REVIEW);
    }

    public BigDecimal calculateTotalApprovedAmount() {
        return scoreResultRepository.findAll()
                .stream()
                .map(result -> result.getApprovedAmount() == null ? BigDecimal.ZERO : result.getApprovedAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public double calculateAverageScore() {
        return scoreResultRepository.findAll()
                .stream()
                .mapToInt(result -> result.getScore() == null ? 0 : result.getScore())
                .average()
                .orElse(0);
    }
}