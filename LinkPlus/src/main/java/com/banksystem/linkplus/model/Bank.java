package com.banksystem.linkplus.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal transactionFlatFee;
    private BigDecimal transactionPercentFee;
    private BigDecimal totalTransactionFee = BigDecimal.ZERO;
    private BigDecimal totalTransferAmount = BigDecimal.ZERO;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Bank() {}

    public Bank(String name, BigDecimal transactionFlatFee, BigDecimal transactionPercentFee) {
        this.name = name;
        this.transactionFlatFee = transactionFlatFee;
        this.transactionPercentFee = transactionPercentFee;
    }
}
