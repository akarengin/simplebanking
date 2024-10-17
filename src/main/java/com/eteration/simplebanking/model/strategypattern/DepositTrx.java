package com.eteration.simplebanking.model.strategypattern;

import javax.persistence.*;

@Entity
@DiscriminatorValue("DepositTrx")
public class DepositTrx extends Trx {

    public DepositTrx () {

    }

    public DepositTrx(double amount) {
        super(amount);
    }

    @Override
    public void process(BankAccount account) {
        account.credit(amount);
    }
}