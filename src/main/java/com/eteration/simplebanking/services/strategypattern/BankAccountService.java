package com.eteration.simplebanking.services.strategypattern;

import com.eteration.simplebanking.dto.BankAccountDTO;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.strategypattern.BankAccount;
import com.eteration.simplebanking.model.strategypattern.Trx;
import com.eteration.simplebanking.repository.strategypattern.BankAccountRepository;
import com.eteration.simplebanking.repository.strategypattern.TrxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TrxRepository trxRepository;

    public BankAccount createAccount(String owner, String accountNumber) {
        BankAccount account = new BankAccount(owner, accountNumber);
        return bankAccountRepository.save(account);
    }

    @Transactional // If an exception is thrown, the transaction is rolled back.
    public void postTransaction(String accountNumber, Trx transaction) throws InsufficientBalanceException {
        BankAccount account = findAccount(accountNumber);
        account.post(transaction);
        //trxRepository.save(transaction); ---- No need to do this. CascadeType.ALL does the job.
        bankAccountRepository.save(account);
    }

    public BankAccountDTO getAccountDetails(String accountNumber) {
        BankAccount account = findAccount(accountNumber);
        return new BankAccountDTO(account);
    }

    public BankAccount findAccount(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber);
    }
}

