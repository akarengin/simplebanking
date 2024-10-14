package com.eteration.simplebanking.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("WithdrawalTransaction")
public class WithdrawalTransaction extends Transaction implements Serializable {

    public WithdrawalTransaction() {

    }

    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Override
    protected String getTransactionType() {
        return "WithdrawalTransaction";
    }

}


