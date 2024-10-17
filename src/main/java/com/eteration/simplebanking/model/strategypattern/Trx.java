package com.eteration.simplebanking.model.strategypattern;

import com.eteration.simplebanking.model.InsufficientBalanceException;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type")
public abstract class Trx {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected double amount;
    protected LocalDateTime date;
    protected String approvalCode;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private BankAccount account;

    public Trx() {

    }

    public Trx(double amount) {
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.approvalCode = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public abstract void process(BankAccount account) throws InsufficientBalanceException;
}
