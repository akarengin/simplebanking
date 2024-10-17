package com.eteration.simplebanking.model.strategypattern;

import com.eteration.simplebanking.model.InsufficientBalanceException;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("WithdrawalTrx")
public class WithdrawalTrx extends Trx implements Serializable {

    public WithdrawalTrx() {

    }

    public WithdrawalTrx(double amount) {
        super(amount);
    }

    @Override
    public void process(BankAccount account) throws InsufficientBalanceException {
        if (account.getBalance() < this.amount) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }
        account.debit(amount);
    }
}

