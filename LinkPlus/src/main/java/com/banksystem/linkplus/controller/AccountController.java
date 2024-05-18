package com.banksystem.linkplus.controller;

import com.banksystem.linkplus.model.Account;
import com.banksystem.linkplus.model.Transaction;
import com.banksystem.linkplus.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account.getName(), account.getBalance()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Account>> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        try {
            return ResponseEntity.ok(accountService.deposit(accountId, amount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        try {
            return ResponseEntity.ok(accountService.withdraw(accountId, amount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{originatingAccountId}/transfer/{resultingAccountId}")
    public ResponseEntity<Transaction> transfer(@PathVariable Long originatingAccountId,
                                                @PathVariable Long resultingAccountId,
                                                @RequestParam BigDecimal amount) {
        try {
            return ResponseEntity.ok(accountService.transfer(originatingAccountId, resultingAccountId, amount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsForAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getTransactionsForAccount(accountId));
    }
}
