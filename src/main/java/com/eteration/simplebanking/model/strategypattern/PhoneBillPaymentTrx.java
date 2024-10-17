package com.eteration.simplebanking.model.strategypattern;

import com.eteration.simplebanking.model.InsufficientBalanceException;

import javax.persistence.*;

@Entity
@DiscriminatorValue("PhoneBillPaymentTrx")
public class PhoneBillPaymentTrx extends Trx {

    private String payee;
    private String phoneNumber;

    public PhoneBillPaymentTrx() {

    }

    public PhoneBillPaymentTrx(double amount, String payee, String phoneNumber) {
        super(amount);
        this.payee = payee;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    @Override
    public void process(BankAccount account) throws InsufficientBalanceException {
        if (account.getBalance() < this.amount) {
            throw new InsufficientBalanceException("Insufficient credit for paying the bill");
        }
        account.debit(amount);
    }
}

