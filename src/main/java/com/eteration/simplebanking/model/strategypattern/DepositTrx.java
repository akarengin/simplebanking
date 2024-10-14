package com.eteration.simplebanking.model.strategypattern;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("DepositTrx")
public class DepositTrx extends Trx implements Serializable {

    public DepositTrx () {

    }

    public DepositTrx(double amount) {
        super(amount);
    }

    @Override
    public void process(BankAccount account) {
        account.setBalance(account.getBalance() + this.amount);
    }
}