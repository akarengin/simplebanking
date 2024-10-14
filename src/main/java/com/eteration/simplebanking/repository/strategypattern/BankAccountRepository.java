package com.eteration.simplebanking.repository.strategypattern;

import com.eteration.simplebanking.model.strategypattern.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByAccountNumber(String accountNumber);
}
