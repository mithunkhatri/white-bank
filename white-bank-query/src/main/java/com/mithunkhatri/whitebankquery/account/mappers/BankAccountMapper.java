package com.mithunkhatri.whitebankquery.account.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.mithunkhatri.whitebankquery.account.controllers.AccountTransactionResponse;
import com.mithunkhatri.whitebankquery.account.controllers.BankAccountBalanceResponse;
import com.mithunkhatri.whitebankquery.account.controllers.BankAccountResponse;
import com.mithunkhatri.whitebankquery.account.models.AccountTransaction;
import com.mithunkhatri.whitebankquery.account.models.BankAccount;

public class BankAccountMapper {

  public static BankAccountResponse docToResponse(BankAccount bankAccount) {
    return new BankAccountResponse(
        bankAccount.getAccountId(),
        bankAccount.getAccountOwner(),
        bankAccount.getBalance(),
        bankAccount.getCreditLine(),
        bankAccount.getStatus());
  }

  public static BankAccountBalanceResponse docToBalanceResponse(BankAccount bankAccount) {
    return new BankAccountBalanceResponse(bankAccount.getBalance());
  }

  public static List<AccountTransactionResponse> docToTransactionResponses(List<AccountTransaction> transactions) {
    return transactions.stream()
        .map(t -> docToTransactionResponse(t))
        .collect(Collectors.toList());
  }

  private static AccountTransactionResponse docToTransactionResponse(AccountTransaction transaction) {
    return new AccountTransactionResponse(
        transaction.getTransactionId(),
        transaction.getTransactionOn(),
        transaction.getType(),
        transaction.getAmount(),
        transaction.getStatus(),
        transaction.getReason());
  }
}
