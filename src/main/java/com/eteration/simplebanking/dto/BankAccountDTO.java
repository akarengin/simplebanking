package com.eteration.simplebanking.dto;

import com.eteration.simplebanking.model.strategypattern.BankAccount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BankAccountDTO {
    private String accountNumber;
    private String owner;
    private double balance;
    private LocalDateTime createDate;
    private List<TransactionDTO> transactions;
    public BankAccountDTO(BankAccount account) {
        this.accountNumber = account.getAccountNumber();
        this.owner = account.getOwner();
        this.balance = account.getBalance();
        this.createDate = account.getCreateDate();
        this.transactions = account.getTransactions().stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

}
