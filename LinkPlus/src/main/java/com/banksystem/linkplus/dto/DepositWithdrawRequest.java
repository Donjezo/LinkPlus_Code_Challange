package com.banksystem.linkplus.dto;

import java.math.BigDecimal;

public class DepositWithdrawRequest {
    private BigDecimal amount;

    // Getters and setters
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
