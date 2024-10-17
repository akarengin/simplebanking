package com.eteration.simplebanking.model.strategypattern;

import com.eteration.simplebanking.model.InsufficientBalanceException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;
    private String accountNumber;
    private double balance;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Trx> transactions = new ArrayList<>();

    public BankAccount() {

    }

    public BankAccount(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0;
        this.createDate = LocalDateTime.now();
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
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public List<Trx> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Trx> transactions) {
        this.transactions = transactions;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void credit(double amount) {
        this.balance += amount;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }

    public void post(Trx transaction) throws InsufficientBalanceException {
        transaction.process(this);
        this.transactions.add(transaction);
        transaction.setAccount(this);
    }
}
