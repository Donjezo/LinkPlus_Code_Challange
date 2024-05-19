package com.banksystem.linkplus.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.banksystem.linkplus.model.Bank;
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
public class BankControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankRepository bankRepository;

    @BeforeEach
    public void setUp() {
        bankRepository.deleteAll();
    }

    @Test
    public void testCreateBank() throws Exception {
        String bankJson = "{\"name\":\"MyBank\",\"transactionFlatFee\":10.00,\"transactionPercentFee\":5.00}";

        mockMvc.perform(post("/api/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bankJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyBank"))
                .andExpect(jsonPath("$.transactionFlatFee").value(10.00))
                .andExpect(jsonPath("$.transactionPercentFee").value(5.00));
    }

    @Test
    public void testGetBankById() throws Exception {
        Bank bank = new Bank("MyBank", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        bankRepository.save(bank);

        mockMvc.perform(get("/api/banks/" + bank.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyBank"))
                .andExpect(jsonPath("$.transactionFlatFee").value(10.00))
                .andExpect(jsonPath("$.transactionPercentFee").value(5.00));
    }

    @Test
    public void testGetAllBanks() throws Exception {
        Bank bank1 = new Bank("MyBank1", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        Bank bank2 = new Bank("MyBank2", BigDecimal.valueOf(20.00), BigDecimal.valueOf(3.00));
        bankRepository.save(bank1);
        bankRepository.save(bank2);

        mockMvc.perform(get("/api/banks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("MyBank1"))
                .andExpect(jsonPath("$[1].name").value("MyBank2"));
    }

    @Test
    public void testGetTotalTransactionFee() throws Exception {
        Bank bank = new Bank("MyBank", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        bank.setTotalTransactionFee(BigDecimal.valueOf(100.00));
        bankRepository.save(bank);

        mockMvc.perform(get("/api/banks/" + bank.getId() + "/total-transaction-fee"))
                .andExpect(status().isOk())
                .andExpect(content().string("100.00"));
    }

    @Test
    public void testGetTotalTransferAmount() throws Exception {
        Bank bank = new Bank("MyBank", BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00));
        bank.setTotalTransferAmount(BigDecimal.valueOf(5000.00));
        bankRepository.save(bank);

        mockMvc.perform(get("/api/banks/" + bank.getId() + "/total-transfer-amount"))
                .andExpect(status().isOk())
                .andExpect(content().string("5000.00"));
    }
}
