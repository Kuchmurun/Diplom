package com.example.bank.credit;

import com.example.bank.client.ClientEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    private BigDecimal requestedAmount;

    private Integer requestedMonths;

    @Enumerated(EnumType.STRING)
    private CreditApplicationStatus status;

    private LocalDateTime createdAt;
}
