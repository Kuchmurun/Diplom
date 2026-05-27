package com.example.bank.credit;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_score_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScoreResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private CreditApplicationEntity application;

    private Integer score;

    @Enumerated(EnumType.STRING)
    private CreditReliabilityLevel reliabilityLevel;

    private BigDecimal approvedAmount;

    private String decisionReason;

    private LocalDateTime calculatedAt;
}