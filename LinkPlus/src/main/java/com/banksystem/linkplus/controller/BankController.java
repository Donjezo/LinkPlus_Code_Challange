package com.banksystem.linkplus.controller;

import com.banksystem.linkplus.model.Bank;
import com.banksystem.linkplus.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping

    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        return ResponseEntity.ok(bankService.createBank(bank.getName(), bank.getTransactionFlatFee(), bank.getTransactionPercentFee()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Bank>> getBankById(@PathVariable Long id) {
        return ResponseEntity.ok(bankService.getBankById(id));
    }

    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        return ResponseEntity.ok(bankService.getAllBanks());
    }

    @GetMapping("/{id}/total-transaction-fee")
    public ResponseEntity<BigDecimal> getTotalTransactionFee(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bankService.getTotalTransactionFee(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}/total-transfer-amount")
    public ResponseEntity<BigDecimal> getTotalTransferAmount(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bankService.getTotalTransferAmount(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
