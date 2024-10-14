package com.eteration.simplebanking.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("DepositTransaction")
public class DepositTransaction extends Transaction implements Serializable {

    public DepositTransaction () {

    }

    public DepositTransaction(double amount) {
        super(amount);
    }

    @Override
    protected String getTransactionType() {
        return "DepositTransaction";
    }

}
