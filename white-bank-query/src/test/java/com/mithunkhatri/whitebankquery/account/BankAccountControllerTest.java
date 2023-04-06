package com.mithunkhatri.whitebankquery.account;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mithunkhatri.whitebankquery.account.controllers.AccountTransactionResponse;
import com.mithunkhatri.whitebankquery.account.exceptions.AccountNotFoundException;
import com.mithunkhatri.whitebankquery.account.models.BankAccount;
import com.mithunkhatri.whitebankquery.account.services.BankAccountService;
import com.mithunkhatri.whitebankquery.utils.InstantUtil;

/**
 * Tests for query endpoints
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BankAccountService bankAccountService;

  @Test
  public void testGetAllAccountsWhenExist() throws Exception {
    String accountId = UUID.randomUUID().toString();
    BankAccount bankAccount = new BankAccount(accountId, "Mithun", BigDecimal.valueOf(1000), BigDecimal.valueOf(100),
        "ACTIVE", Collections.emptyList());

    when(bankAccountService.getAll()).thenReturn(Arrays.asList(bankAccount));
    mockMvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].accountId").value(accountId));
  }

  @Test
  public void testGetAllAccountsWhenNotExist() throws Exception {
    when(bankAccountService.getAll()).thenReturn(Collections.emptyList());
    mockMvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  public void testGetRedAccountsWhenExist() throws Exception {
    String accountId = UUID.randomUUID().toString();
    BankAccount bankAccount = new BankAccount(accountId, "Mithun", BigDecimal.valueOf(-100), BigDecimal.valueOf(100),
        "ACTIVE", Collections.emptyList());

    when(bankAccountService.getAllRed()).thenReturn(Arrays.asList(bankAccount));
    mockMvc.perform(get("/accounts/red").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].accountId").value(accountId));
  }

  @Test
  public void testGetRedAccountsWhenNotExist() throws Exception {
    when(bankAccountService.getAllRed()).thenReturn(Collections.emptyList());
    mockMvc.perform(get("/accounts/red").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  public void testGetAccountByIdWhenExist() throws Exception {
    String accountId = UUID.randomUUID().toString();
    BankAccount bankAccount = new BankAccount(accountId, "Mithun", BigDecimal.valueOf(1000), BigDecimal.valueOf(100),
        "ACTIVE", Collections.emptyList());

    when(bankAccountService.validateAndGet(anyString())).thenReturn(bankAccount);
    mockMvc.perform(get("/accounts/" + accountId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountId").value(accountId));
  }

  @Test
  public void testGetAccountByIdWhenNotExist() throws Exception {
    String accountId = UUID.randomUUID().toString();
    when(bankAccountService.validateAndGet(anyString())).thenThrow(new AccountNotFoundException(accountId));
    mockMvc.perform(get("/accounts/" + accountId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetAccountBalanceWhenExist() throws Exception {
    String accountId = UUID.randomUUID().toString();
    BankAccount bankAccount = new BankAccount(accountId, "Mithun", BigDecimal.valueOf(1000), BigDecimal.valueOf(100),
        "ACTIVE", Collections.emptyList());

    when(bankAccountService.validateAndGet(anyString())).thenReturn(bankAccount);
    mockMvc.perform(get("/accounts/" + accountId + "/balance").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(1000)));
  }

  @Test
  public void testGetAccountBalanceWhenNotExist() throws Exception {
    String accountId = UUID.randomUUID().toString();
    when(bankAccountService.validateAndGet(anyString())).thenThrow(new AccountNotFoundException(accountId));
    mockMvc.perform(get("/accounts/" + accountId + "/balance").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @ParameterizedTest
  @ValueSource(strings = { "", "2023-04-01" })
  public void testGetAccountTransactionsWhenExist(String since) throws Exception {
    String accountId = UUID.randomUUID().toString();
    String transactionId = UUID.randomUUID().toString();
    AccountTransactionResponse response = new AccountTransactionResponse();
    response.setTransactionId(transactionId);
    response.setTransactionOn(InstantUtil.toInstant("2023-04-05"));
    response.setAmount(BigDecimal.valueOf(10));
    response.setType("DEBIT");

    if (since.isEmpty()) {
      when(bankAccountService.getAccountTransactions(anyString())).thenReturn(Arrays.asList(response));
      mockMvc.perform(get("/accounts/" + accountId + "/transactions").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].transactionId").value(transactionId));
    } else {
      when(bankAccountService.getAccountTransactions(anyString(), any()))
          .thenReturn(Arrays.asList(response));
      mockMvc
          .perform(
              get("/accounts/" + accountId + "/transactions?since=" + since).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].transactionId").value(transactionId));
    }

  }

  @ParameterizedTest
  @ValueSource(strings = { "", "2023-04-01" })
  public void testGetAccountTransactionsWhenNotExist(String since) throws Exception {
    String accountId = UUID.randomUUID().toString();
    if (since.isEmpty()) {
      when(bankAccountService.getAccountTransactions(anyString())).thenReturn(Collections.emptyList());
      mockMvc.perform(get("/accounts/" + accountId + "/transactions").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").isEmpty());
    } else {
      when(bankAccountService.getAccountTransactions(anyString(), any()))
          .thenReturn(Collections.emptyList());
      mockMvc
          .perform(
              get("/accounts/" + accountId + "/transactions?since=" + since).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").isEmpty());
    }
  }
}
