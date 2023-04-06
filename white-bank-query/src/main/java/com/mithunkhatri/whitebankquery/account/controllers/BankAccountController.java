package com.mithunkhatri.whitebankquery.account.controllers;

import static com.mithunkhatri.whitebankquery.account.mappers.BankAccountMapper.docToBalanceResponse;
import static com.mithunkhatri.whitebankquery.account.mappers.BankAccountMapper.docToResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mithunkhatri.whitebankquery.account.services.BankAccountService;
import com.mithunkhatri.whitebankquery.utils.InstantUtil;

import lombok.AllArgsConstructor;

/**
 * Bank account query controller
 */
@RestController
@RequestMapping(value = "/accounts")
@AllArgsConstructor
public class BankAccountController {

  private BankAccountService bankAccountService;

  /**
   * Gets all bank accounts. Does not include transaction details
   */
  @GetMapping
  public List<BankAccountResponse> all() {

    return this.bankAccountService.getAll()
        .stream()
        .map(bankAccount -> docToResponse(bankAccount))
        .collect(Collectors.toList());
  }

  /**
   * Gets all the bank account details which are in red. i.e., balance is
   * negative.
   */
  @GetMapping(value = "/red")
  public List<BankAccountResponse> red() {
    return this.bankAccountService.getAllRed()
        .stream()
        .map(bankAccount -> docToResponse(bankAccount))
        .collect(Collectors.toList());
  }

  /**
   * Gets account details by account id
   */
  @GetMapping(value = "/{accountId}")
  public BankAccountResponse account(@PathVariable String accountId) {
    return docToResponse(this.bankAccountService.validateAndGet(accountId));
  }

  /**
   * Gets account current balance
   */
  @GetMapping(value = "/{accountId}/balance")
  public BankAccountBalanceResponse balance(@PathVariable String accountId) {
    return docToBalanceResponse(this.bankAccountService.validateAndGet(accountId));
  }

  /**
   * Gets all the transactions for the account id since a provided date. If the
   * date is not provided, then all the transactions are returned.
   */
  @GetMapping(value = "/{accountId}/transactions")
  public List<AccountTransactionResponse> accountTransactions(@PathVariable String accountId,
      @RequestParam(required = false) String since) {

    if (since != null) {
      return this.bankAccountService.getAccountTransactions(accountId, InstantUtil.toInstant(since));
    }
    return this.bankAccountService.getAccountTransactions(accountId);
  }
}
