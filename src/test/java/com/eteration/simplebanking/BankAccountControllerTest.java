package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.eteration.simplebanking.controller.strategypattern.BankAccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.strategypattern.BankAccount;
import com.eteration.simplebanking.model.strategypattern.DepositTrx;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.strategypattern.WithdrawalTrx;
import com.eteration.simplebanking.services.strategypattern.BankAccountService;
import com.eteration.simplebanking.dto.BankAccountDTO;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class BankAccountControllerTest {

    @Spy
    @InjectMocks
    private BankAccountController controller;

    @Mock
    private BankAccountService service;

    @Test
    public void givenOwnerAndAccountNumber_createAccount_thenReturnJson() {

        BankAccount account = new BankAccount("Kerem Karaca", "17892");

        doReturn(account).when(service).createAccount("Kerem Karaca", "17892");

        ResponseEntity<BankAccount> result = controller.createAccount("Kerem Karaca", "17892");

        verify(service, times(1)).createAccount("Kerem Karaca", "17892");
        assertEquals("Kerem Karaca", result.getBody().getOwner());
        assertEquals("17892", result.getBody().getAccountNumber());
    }

    @Test
    public void givenAccountNumber_credit_thenReturnJson() {

        BankAccount account = new BankAccount("Kerem Karaca", "17892");
        DepositTrx depositTrx = new DepositTrx(1000.0);

        doReturn(account).when(service).findAccount("17892");
        doNothing().when(service).postTransaction(eq("17892"), any(DepositTrx.class));

        ResponseEntity<TransactionStatus> result = controller.credit("17892", depositTrx);

        verify(service, times(1)).postTransaction(eq("17892"), any(DepositTrx.class));
        assertEquals("OK", result.getBody().getStatus());
        assertNotNull(result.getBody().getApprovalCode());
    }


    @Test
    public void givenAccountNumber_debit_thenReturnJson() throws InsufficientBalanceException {

        BankAccount account = new BankAccount("Kerem Karaca", "17892");
        WithdrawalTrx withdrawalTrx = new WithdrawalTrx(50.0);

        doReturn(account).when(service).findAccount("17892");
        doNothing().when(service).postTransaction(eq("17892"), any(WithdrawalTrx.class));

        ResponseEntity<TransactionStatus> result = controller.debit("17892", withdrawalTrx);

        verify(service, times(1)).postTransaction(eq("17892"), any(WithdrawalTrx.class));
        assertEquals("OK", result.getBody().getStatus());
        assertNotNull(result.getBody().getApprovalCode());
    }


    @Test
    public void givenAccountNumber_debitMoreThanBalance_throwsInsufficientBalanceException() {

        WithdrawalTrx withdrawalTrx = new WithdrawalTrx(5000.0);

        doThrow(new InsufficientBalanceException("Insufficient balance")).when(service).postTransaction(eq("17892"), any(WithdrawalTrx.class));

        ResponseEntity<TransactionStatus> result = controller.debit("17892", withdrawalTrx);

        verify(service, times(1)).postTransaction(eq("17892"), any(WithdrawalTrx.class));
        assertEquals("FAILED", result.getBody().getStatus());
        assertNull(result.getBody().getApprovalCode());
    }


    @Test
    public void givenAccountNumber_getAccountDetails_thenReturnJson() {

        BankAccount account = new BankAccount("Kerem Karaca", "17892");

        BankAccountDTO accountDTO = new BankAccountDTO(account);
        accountDTO.setAccountNumber("17892");
        accountDTO.setOwner("Kerem Karaca");
        accountDTO.setBalance(950.0);

        doReturn(accountDTO).when(service).getAccountDetails("17892");

        ResponseEntity<BankAccountDTO> result = controller.getAccountDetails("17892");

        verify(service, times(1)).getAccountDetails("17892");
        assertEquals("17892", result.getBody().getAccountNumber());
        assertEquals("Kerem Karaca", result.getBody().getOwner());
        assertEquals(950.0, result.getBody().getBalance(), 0.001);
    }

}
