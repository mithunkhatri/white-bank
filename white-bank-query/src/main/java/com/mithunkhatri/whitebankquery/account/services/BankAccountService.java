package com.mithunkhatri.whitebankquery.account.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

import com.mithunkhatri.whitebankquery.account.controllers.AccountTransactionResponse;
import com.mithunkhatri.whitebankquery.account.mappers.BankAccountMapper;
import com.mithunkhatri.whitebankquery.account.models.BankAccount;
import com.mithunkhatri.whitebankquery.account.queries.FindAccountById;
import com.mithunkhatri.whitebankquery.account.queries.FindAllAccount;
import com.mithunkhatri.whitebankquery.account.queries.FindAllRedAccount;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BankAccountService {

  private QueryGateway queryGateway;

  public CompletableFuture<List<BankAccount>> getAll() {

    return this.queryGateway.query(new FindAllAccount(),
        ResponseTypes.multipleInstancesOf(BankAccount.class));
  }

  public List<BankAccount> getAllRed() {
    return this.queryGateway.query(new FindAllRedAccount(BigDecimal.ZERO),
        ResponseTypes.multipleInstancesOf(BankAccount.class)).join();
  }

  public BankAccount getAccountById(String accountId) {
    return this.queryGateway.query(new FindAccountById(accountId), BankAccount.class).join();
  }

  public List<AccountTransactionResponse> getAccountTransactions(String accountId) {
    BankAccount bankAccount = getAccountById(accountId);
    if (bankAccount == null) {
      return Collections.emptyList();
    }
    return BankAccountMapper.docToTransactionResponses(bankAccount.getTransactions());
  }

  public List<AccountTransactionResponse> getAccountTransactions(String accountId, Instant since) {
    BankAccount bankAccount = getAccountById(accountId);
    if (bankAccount == null) {
      return Collections.emptyList();
    }
    return BankAccountMapper.docToTransactionResponses(
      bankAccount.getTransactions()
      .stream()
      .filter(transaction -> transaction.getTransactionOn().isAfter(since))
      .collect(Collectors.toList())
    );
  }
}
