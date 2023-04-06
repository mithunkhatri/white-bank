package com.mithunkhatri.whitebankquery.account.handlers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import com.mithunkhatri.whitebankcommon.account.events.AmountCreditedEvent;
import com.mithunkhatri.whitebankcommon.account.events.AmountDebitPendingEvent;
import com.mithunkhatri.whitebankcommon.account.events.AmountDebitedEvent;
import com.mithunkhatri.whitebankcommon.account.events.BankAccountCreatedEvent;
import com.mithunkhatri.whitebankquery.account.models.AccountTransaction;
import com.mithunkhatri.whitebankquery.account.models.BankAccount;
import com.mithunkhatri.whitebankquery.account.repositories.BankAccountRepository;

import lombok.AllArgsConstructor;

/**
 * Event hanlders to sync the events emitted by command service.
 */
@Component
@AllArgsConstructor
public class BankAccountEventHandler {

  private BankAccountRepository bankAccountRepository;

  @EventHandler
  public void handle(BankAccountCreatedEvent event) {
    Optional<BankAccount> accountOptional = this.bankAccountRepository.findById(event.getAccountId());
    // Check if account already exists with the accountId
    if (accountOptional.isPresent()) {
      throw new RuntimeException("Account already exists with id : " + event.getAccountId());
    }
    this.bankAccountRepository
        .save(new BankAccount(
            event.getAccountId(),
            event.getAccountOwner(),
            event.getInitialDeposit(),
            event.getCreditLine(),
            "ACTIVE",
            new ArrayList<>()));
  }

  /**
   * Handles debit event and saves the transaction and the new balance
   */
  @EventHandler
  public void handle(AmountDebitedEvent event, @Timestamp Instant actionTime) {
    Optional<BankAccount> accountOptional = this.bankAccountRepository.findById(event.getAccountId());
    if (!accountOptional.isPresent()) {
      throw new RuntimeException("Account does not exist with id : " + event.getAccountId());
    }
    BankAccount account = accountOptional.get();
    account.getTransactions()
        .add(new AccountTransaction(event.getTransactionId(), actionTime, "DEBIT", event.getAmount()));
    account.setBalance(event.getNewBalance());
    this.bankAccountRepository.save(account);
  }

  /**
   * Handles debit event and saves the transaction with status and reason
   */
  @EventHandler
  public void handle(AmountDebitPendingEvent event, @Timestamp Instant actionTime) {
    Optional<BankAccount> accountOptional = this.bankAccountRepository.findById(event.getAccountId());
    if (!accountOptional.isPresent()) {
      throw new RuntimeException("Account does not exist with id : " + event.getAccountId());
    }
    BankAccount account = accountOptional.get();
    account.getTransactions()
        .add(new AccountTransaction(event.getTransactionId(), actionTime, "DEBIT", event.getAmount(), event.getStatus(),
            event.getReason()));
    this.bankAccountRepository.save(account);
  }

  /**
   * Handles credit event and saves the transaction and the new balance
   */
  @EventHandler
  public void handle(AmountCreditedEvent event, @Timestamp Instant actionTime) {
    Optional<BankAccount> accountOptional = this.bankAccountRepository.findById(event.getAccountId());
    if (!accountOptional.isPresent()) {
      throw new RuntimeException("Account does not exist with id : " + event.getAccountId());
    }
    BankAccount account = accountOptional.get();
    account.getTransactions()
        .add(new AccountTransaction(event.getTransactionId(), actionTime, "CREDIT", event.getAmount()));
    account.setBalance(event.getNewBalance());
    this.bankAccountRepository.save(account);
  }
}
