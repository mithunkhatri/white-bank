package com.mithunkhatri.whitebankcmd.account;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mithunkhatri.whitebankcmd.account.controllers.BankAccountRequest;
import com.mithunkhatri.whitebankcmd.account.controllers.PaymentRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testCreateBankAccount() throws Exception {
    BankAccountRequest bankAccountRequest = new BankAccountRequest(
        "Mithun",
        BigDecimal.valueOf(1000),
        BigDecimal.valueOf(100));

    mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(bankAccountRequest)))
        .andExpect(status().isCreated());
  }

  @ParameterizedTest
  @ValueSource(doubles = { 0, 0.0, -10 })
  public void testCreateBankAccountWithInvalidInitialBalance(double initialBalance) throws Exception {
    BankAccountRequest bankAccountRequest = new BankAccountRequest(
        "Mithun",
        BigDecimal.valueOf(initialBalance),
        BigDecimal.valueOf(100));

    mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(bankAccountRequest)))
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = { "" })
  public void testCreateBankAccountWithInvalidAccountOwner(String accountOwner) throws Exception {
    BankAccountRequest bankAccountRequest = new BankAccountRequest(
        accountOwner,
        BigDecimal.valueOf(1000),
        BigDecimal.valueOf(100));

    mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(bankAccountRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testDebitPayment() throws Exception {

    String accountId = UUID.randomUUID().toString();

    mockMvc.perform(put("/accounts/" + accountId + "/debit").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new PaymentRequest(BigDecimal.valueOf(10)))))
        .andExpect(status().isAccepted());
  }

  @ParameterizedTest
  @ValueSource(doubles = { 0, -10 })
  public void testDebitPaymentWithInvalidAmount(double amount) throws Exception {
    String accountId = UUID.randomUUID().toString();

    mockMvc.perform(put("/accounts/" + accountId + "/debit").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new PaymentRequest(BigDecimal.valueOf(amount)))))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreditPayment() throws Exception {

    String accountId = UUID.randomUUID().toString();

    mockMvc.perform(put("/accounts/" + accountId + "/credit").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new PaymentRequest(BigDecimal.valueOf(10)))))
        .andExpect(status().isAccepted());
  }

  @ParameterizedTest
  @ValueSource(doubles = { 0, -10 })
  public void testCreditPaymentWithInvalidAmount(double amount) throws Exception {
    String accountId = UUID.randomUUID().toString();

    mockMvc.perform(put("/accounts/" + accountId + "/credit").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new PaymentRequest(BigDecimal.valueOf(amount)))))
        .andExpect(status().isBadRequest());
  }
}
