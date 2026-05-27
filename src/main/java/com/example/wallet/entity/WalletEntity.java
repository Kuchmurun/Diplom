package com.example.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallets")
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wall_seq")
    @SequenceGenerator(name = "wall_seq", sequenceName = "public.wallets_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 35)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 35)
    private String lastName;

    @Column(name = "middle_name", nullable = false, length = 35)
    private String middleName;

    @Column(name = "passport_id", nullable = false, length = 10)
    private Integer passportId;

    @Column(name = "rub")
    private BigDecimal rub;

    @Column(name = "usd")
    private BigDecimal usd;

    @Column(name = "eur")
    private BigDecimal eur;

    @Column(name = "wallet_status", nullable = false)
    private String walletStatus;

    @JdbcTypeCode(Types.BOOLEAN)
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public String getDefaultWalletStatus() {
        return "AVAILABLE";
    }
}
