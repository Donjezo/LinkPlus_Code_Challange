package com.banksystem.linkplus.service;

import com.banksystem.linkplus.model.Account;
import com.banksystem.linkplus.model.Bank;
import com.banksystem.linkplus.model.Transaction;
import com.banksystem.linkplus.repository.AccountRepository;
import com.banksystem.linkplus.repository.BankRepository;
import com.banksystem.linkplus.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankRepository bankRepository;

    public Account createAccount(String name, BigDecimal balance) {
        Account account = new Account(name, balance);
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) throws Exception {
        if (!accountRepository.existsById(id)) {
            throw new Exception("Account not found");
        }
        accountRepository.deleteById(id);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

   /* public Account deposit(Long accountId, BigDecimal amount) throws Exception {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new Exception("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // pjesa e transaksioneve
        Transaction transaction = new Transaction(amount, accountId, accountId, "Deposit");
        transactionRepository.save(transaction);

        return account;
    }*/

    public Account deposit(Long accountId, BigDecimal amount) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new Exception("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    public Account withdraw(Long accountId, BigDecimal amount) throws Exception {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new Exception("LLogaria nuk u gjend"));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new Exception("Not enough funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        // transaksionet
        Transaction transaction = new Transaction(amount, accountId, accountId, "Withdraw");
        transactionRepository.save(transaction);

        return account;
    }

    public Transaction transfer(Long bankId, Long originatingAccountId, Long resultingAccountId, BigDecimal amount) throws Exception {
        Account originatingAccount = accountRepository.findById(originatingAccountId)
                .orElseThrow(() -> new Exception("Originating account not found"));
        Account resultingAccount = accountRepository.findById(resultingAccountId)
                .orElseThrow(() -> new Exception("Resulting account not found"));

        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new Exception("Bank not found"));

        BigDecimal fee = bank.getTransactionFlatFee().add(amount.multiply(bank.getTransactionPercentFee().divide(new BigDecimal(100))));
        BigDecimal totalAmount = amount.add(fee);

        if (originatingAccount.getBalance().compareTo(totalAmount) < 0) {
            throw new Exception("Not enough funds");
        }

        originatingAccount.setBalance(originatingAccount.getBalance().subtract(totalAmount));
        resultingAccount.setBalance(resultingAccount.getBalance().add(amount));

        accountRepository.save(originatingAccount);
        accountRepository.save(resultingAccount);

        bank.setTotalTransactionFee(bank.getTotalTransactionFee().add(fee));
        bank.setTotalTransferAmount(bank.getTotalTransferAmount().add(amount));
        bankRepository.save(bank);

        // Record the transaction
        Transaction transaction = new Transaction(amount, originatingAccountId, resultingAccountId, "Transfer");
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsForAccount(Long accountId) {
        return transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getOriginatingAccountId().equals(accountId)
                        || transaction.getResultingAccountId().equals(accountId))
                .toList();
    }
}
