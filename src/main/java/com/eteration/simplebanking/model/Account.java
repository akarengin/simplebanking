package com.eteration.simplebanking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;
    private String accountNumber;
    private LocalDateTime createDate;

    private double balance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public Account () {

    }

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate = LocalDateTime.now();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public DepositTransaction deposit(double amount) {
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        post(depositTransaction);
        return depositTransaction;
    }

    public WithdrawalTransaction withdraw(double amount) throws InsufficientBalanceException{
        if (this.balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount);
        post(withdrawalTransaction);
        return withdrawalTransaction;
    }

    public DepositTransaction post(DepositTransaction transaction) {
        this.balance += transaction.getAmount();
        transaction.setAccount(this);
        transactions.add(transaction);
        return transaction;
    }

    public WithdrawalTransaction post(WithdrawalTransaction transaction) {
        this.balance -= transaction.getAmount();
        transaction.setAccount(this);
        transactions.add(transaction);
        return transaction;
    }

}
