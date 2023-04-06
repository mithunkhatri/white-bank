package com.mithunkhatri.whitebankquery.account.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

import com.mithunkhatri.whitebankquery.account.controllers.AccountTransactionResponse;
import com.mithunkhatri.whitebankquery.account.exceptions.AccountNotFoundException;
import com.mithunkhatri.whitebankquery.account.mappers.BankAccountMapper;
import com.mithunkhatri.whitebankquery.account.models.BankAccount;
import com.mithunkhatri.whitebankquery.account.models.BankAccounts;
import com.mithunkhatri.whitebankquery.account.queries.FindAccountById;
import com.mithunkhatri.whitebankquery.account.queries.FindAllAccount;
import com.mithunkhatri.whitebankquery.account.queries.FindAllRedAccount;

import lombok.AllArgsConstructor;

/**
 * Service to dispatch queries
 */
@Component
@AllArgsConstructor
public class BankAccountService {

  private QueryGateway queryGateway;

  public List<BankAccount> getAll() {

    return this.queryGateway.query(new FindAllAccount(), BankAccounts.class).join().getBankAccounts();
  }

  public List<BankAccount> getAllRed() {
    return this.queryGateway.query(new FindAllRedAccount(BigDecimal.ZERO), BankAccounts.class).join().getBankAccounts();
  }

  public BankAccount getAccountById(String accountId) {
    return this.queryGateway.query(new FindAccountById(accountId), BankAccount.class).join();
  }

  public List<AccountTransactionResponse> getAccountTransactions(String accountId) {
    BankAccount bankAccount = validateAndGet(accountId);
    return BankAccountMapper.docToTransactionResponses(bankAccount.getTransactions());
  }

  public List<AccountTransactionResponse> getAccountTransactions(String accountId, Instant since) {
    BankAccount bankAccount = validateAndGet(accountId);
    return BankAccountMapper.docToTransactionResponses(
        bankAccount.getTransactions()
            .stream()
            .filter(transaction -> transaction.getTransactionOn().isAfter(since))
            .collect(Collectors.toList()));
  }

  public BankAccount validateAndGet(String accountId) {
    BankAccount bankAccount = this.getAccountById(accountId);
    if (bankAccount == null) {
      throw new AccountNotFoundException(accountId);
    }
    return bankAccount;
  }
}
