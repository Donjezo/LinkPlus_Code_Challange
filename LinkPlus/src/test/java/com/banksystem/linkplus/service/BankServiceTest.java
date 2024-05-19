package com.banksystem.linkplus.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.banksystem.linkplus.model.Bank;
import com.banksystem.linkplus.repository.BankRepository;
import com.banksystem.linkplus.service.BankService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBank() {
        Bank bank = new Bank("MyBank", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        when(bankRepository.save(any(Bank.class))).thenReturn(bank);

        Bank createdBank = bankService.createBank("MyBank", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        assertNotNull(createdBank);
        assertEquals("MyBank", createdBank.getName());
        assertEquals(BigDecimal.valueOf(10.00), createdBank.getTransactionFlatFee());
        assertEquals(BigDecimal.valueOf(5.00), createdBank.getTransactionPercentFee());
    }

    @Test
    public void testGetBankById() {
        Bank bank = new Bank("MyBank", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        when(bankRepository.findById(1L)).thenReturn(Optional.of(bank));

        Optional<Bank> foundBank = bankService.getBankById(1L);
        assertTrue(foundBank.isPresent());
        assertEquals("MyBank", foundBank.get().getName());
    }

    @Test
    public void testGetAllBanks() {
        Bank bank1 = new Bank("MyBank1", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        Bank bank2 = new Bank("MyBank2", BigDecimal.valueOf(20.00), BigDecimal.valueOf(3.00));
        when(bankRepository.findAll()).thenReturn(Arrays.asList(bank1, bank2));

        List<Bank> banks = bankService.getAllBanks();
        assertEquals(2, banks.size());
    }

    @Test
    public void testGetTotalTransactionFee() throws Exception {
        Bank bank = new Bank("MyBank", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        bank.setTotalTransactionFee(BigDecimal.valueOf(100.00));
        when(bankRepository.findById(1L)).thenReturn(Optional.of(bank));

        BigDecimal totalTransactionFee = bankService.getTotalTransactionFee(1L);
        assertEquals(BigDecimal.valueOf(100.00), totalTransactionFee);
    }

    @Test
    public void testGetTotalTransferAmount() throws Exception {
        Bank bank = new Bank("MyBank", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        bank.setTotalTransferAmount(BigDecimal.valueOf(5000.00));
        when(bankRepository.findById(1L)).thenReturn(Optional.of(bank));

        BigDecimal totalTransferAmount = bankService.getTotalTransferAmount(1L);
        assertEquals(BigDecimal.valueOf(5000.00), totalTransferAmount);
    }

    @Test
    public void testBankNotFoundException() {
        when(bankRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            bankService.getTotalTransactionFee(1L);
        });
        assertEquals("Bank not found", exception.getMessage());

        exception = assertThrows(Exception.class, () -> {
            bankService.getTotalTransferAmount(1L);
        });
        assertEquals("Bank not found", exception.getMessage());
    }
}
