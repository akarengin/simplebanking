package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public String creditAccount(String accountNumber, double amount){
        Account account = accountRepository.findByAccountNumber(accountNumber);
        DepositTransaction depositTransaction = null;
        if (account != null) {
            depositTransaction = account.deposit(amount);
            transactionRepository.save(depositTransaction);
            accountRepository.save(account);
            return depositTransaction.getApprovalCode();
        }
        return null;
    }

    public String debitAccount(String accountNumber, double amount) throws InsufficientBalanceException {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        WithdrawalTransaction withdrawalTransaction = null;
        if (account != null) {
            withdrawalTransaction = account.withdraw(amount);
            transactionRepository.save(withdrawalTransaction);
            accountRepository.save(account);
            return withdrawalTransaction.getApprovalCode();
        } else {
            return null;
        }
    }
}
