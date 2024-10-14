package com.eteration.simplebanking.controller.strategypattern;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.BankAccountDTO;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.model.strategypattern.BankAccount;
import com.eteration.simplebanking.model.strategypattern.DepositTrx;
import com.eteration.simplebanking.model.strategypattern.WithdrawalTrx;
import com.eteration.simplebanking.services.strategypattern.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v2")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/create/{owner}/{accountNumber}")
    public ResponseEntity<BankAccount> createAccount(@PathVariable String owner, @PathVariable String accountNumber) {
        BankAccount newAccount = bankAccountService.createAccount(owner, accountNumber);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTrx transaction) {
        DepositTrx depositTrx = new DepositTrx(transaction.getAmount());
        try {
            bankAccountService.postTransaction(accountNumber, depositTrx);
            TransactionStatus status = new TransactionStatus("OK", depositTrx.getApprovalCode());
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new TransactionStatus("FAILED", null));
        }
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTrx transaction) {
        WithdrawalTrx withdrawalTrx = new WithdrawalTrx(transaction.getAmount());
        try {
            bankAccountService.postTransaction(accountNumber, withdrawalTrx);
            TransactionStatus status = new TransactionStatus("OK", withdrawalTrx.getApprovalCode());
            return ResponseEntity.ok(status);
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.badRequest().body(new TransactionStatus("FAILED", null));
        }
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountDTO> getAccountDetails(@PathVariable String accountNumber) {
        BankAccountDTO accountDTO = bankAccountService.getAccountDetails(accountNumber);
        return ResponseEntity.ok(accountDTO);
    }

}
