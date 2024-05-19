package com.banksystem.linkplus.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.banksystem.linkplus.model.Account;
import com.banksystem.linkplus.model.Transaction;
import com.banksystem.linkplus.repository.AccountRepository;
import com.banksystem.linkplus.repository.TransactionRepository;
import com.banksystem.linkplus.repository.BankRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount() {
        Account account = new Account("Donjete Zogaj", BigDecimal.valueOf(1000.00));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccount("Donjete Zogaj", BigDecimal.valueOf(1000.00));
        assertNotNull(createdAccount);
        assertEquals("Donjete Zogaj", createdAccount.getName());
        assertEquals(BigDecimal.valueOf(1000.00), createdAccount.getBalance());
    }

    @Test
    public void testDeleteAccount() throws Exception {
        when(accountRepository.existsById(1L)).thenReturn(true);
        doNothing().when(accountRepository).deleteById(1L);

        accountService.deleteAccount(1L);
        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeposit() throws Exception {
        Account account = new Account("Donjete Zogaj", BigDecimal.valueOf(1000.00));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.deposit(1L, BigDecimal.valueOf(200.00));
        assertEquals(BigDecimal.valueOf(1200.00), updatedAccount.getBalance());
    }

    @Test
    public void testWithdraw() throws Exception {
        Account account = new Account("Donjete Zogaj", BigDecimal.valueOf(1000.00));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.withdraw(1L, BigDecimal.valueOf(200.00));
        assertEquals(BigDecimal.valueOf(800.00), updatedAccount.getBalance());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        Account account = new Account("Donjete Zogaj", BigDecimal.valueOf(100.00));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Exception exception = assertThrows(Exception.class, () -> {
            accountService.withdraw(1L, BigDecimal.valueOf(200.00));
        });
        assertEquals("Not enough funds", exception.getMessage());
    }
}
