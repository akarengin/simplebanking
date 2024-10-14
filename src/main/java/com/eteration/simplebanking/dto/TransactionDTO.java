package com.eteration.simplebanking.dto;

import com.eteration.simplebanking.model.strategypattern.Trx;

import java.time.LocalDateTime;

public class TransactionDTO {
    private String type;
    private LocalDateTime date;
    private double amount;
    private String approvalCode;

    public TransactionDTO(Trx transaction) {
        this.type = transaction.getClass().getSimpleName();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.approvalCode = transaction.getApprovalCode();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

}
