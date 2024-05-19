package com.banksystem.linkplus.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.banksystem.linkplus.model.Account;
import com.banksystem.linkplus.repository.AccountRepository;
import com.banksystem.linkplus.repository.TransactionRepository;
import com.banksystem.linkplus.repository.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateAccount() throws Exception {
        String accountJson = "{\"name\":\"Donjete Zogaj\",\"balance\":1000.00}";

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Donjete Zogaj"))
                .andExpect(jsonPath("$.balance").value(1000.00));
    }

    @Test
    public void testDeposit() throws Exception {
        Account account = new Account("Donjete Zogaj", BigDecimal.valueOf(1000.00));
        accountRepository.save(account);

        String depositJson = "{\"amount\":200.00}";

        mockMvc.perform(post("/api/accounts/" + account.getId() + "/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(depositJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1200.00));
    }

    @Test
    public void testWithdraw() throws Exception {
        Account account = new Account("Donjete Zogaj", BigDecimal.valueOf(1000.00));
        accountRepository.save(account);

        String withdrawJson = "{\"amount\":200.00}";

        mockMvc.perform(post("/api/accounts/" + account.getId() + "/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withdrawJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(800.00));
    }


}
