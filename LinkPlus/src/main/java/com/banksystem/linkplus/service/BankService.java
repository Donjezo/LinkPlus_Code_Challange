package com.banksystem.linkplus.service;

import com.banksystem.linkplus.model.Bank;
import com.banksystem.linkplus.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public Bank createBank(String name, BigDecimal flatFee, BigDecimal percentFee) {
        Bank bank = new Bank(name, flatFee, percentFee);
        return bankRepository.save(bank);
    }

    public Optional<Bank> getBankById(Long id) {
        return bankRepository.findById(id);
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public BigDecimal getTotalTransactionFee(Long bankId) throws Exception {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new Exception("Bank not found"));
        return bank.getTotalTransactionFee();
    }

    public BigDecimal getTotalTransferAmount(Long bankId) throws Exception {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new Exception("Bank not found"));
        return bank.getTotalTransferAmount();
    }
}