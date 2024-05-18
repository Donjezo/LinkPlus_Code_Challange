package com.banksystem.linkplus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private Long originatingAccountId;
    private Long resultingAccountId;
    private String reason;

    // Default constructor for JPA
    public Transaction() {}

    // Constructor
    public Transaction(BigDecimal amount, Long originatingAccountId, Long resultingAccountId, String reason) {
        this.amount = amount;
        this.originatingAccountId = originatingAccountId;
        this.resultingAccountId = resultingAccountId;
        this.reason = reason;
    }
}
