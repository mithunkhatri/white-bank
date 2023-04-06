package com.mithunkhatri.whitebankcmd.account;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.mithunkhatri.whitebankcmd.account.commands.CreateBankAccountCommand;
import com.mithunkhatri.whitebankcmd.account.commands.CreditAmountCommand;
import com.mithunkhatri.whitebankcmd.account.commands.DebitAmountCommand;
import com.mithunkhatri.whitebankcommon.account.events.AmountCreditedEvent;
import com.mithunkhatri.whitebankcommon.account.events.AmountDebitPendingEvent;
import com.mithunkhatri.whitebankcommon.account.events.AmountDebitedEvent;
import com.mithunkhatri.whitebankcommon.account.events.BankAccountCreatedEvent;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Bank Account aggregate domain object consisting of parameters, command
 * handling and event sourcing
 */
@Aggregate
@NoArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class BankAccountAggregate {

  @AggregateIdentifier
  private String accountId;

  private String accountOwner;
  private BigDecimal balance;
  private BigDecimal creditLine;
  private String status;

  // Handle create bank account command
  @CommandHandler
  public BankAccountAggregate(CreateBankAccountCommand command) {
    AggregateLifecycle.apply(
        new BankAccountCreatedEvent(
            command.getAccountId(),
            command.getAccountOwner(),
            command.getInitialDeposit(),
            command.getCreditLine()));
  }

  // Event source and setting the initial state of the aggregate
  @EventSourcingHandler
  public void on(BankAccountCreatedEvent event) {
    this.accountId = event.getAccountId();
    this.accountOwner = event.getAccountOwner();
    this.balance = event.getInitialDeposit();
    this.creditLine = event.getCreditLine();
    this.status = "ACTIVE"; // Assuming the account to be active right after its created
  }

  @CommandHandler
  public void handle(DebitAmountCommand command) {
    // New balance is calculated based on the debit amount
    // Its calculated in the command service so that query service can avoid any
    // calculation
    BigDecimal newBalance = this.balance.subtract(command.getAmount());

    // Checking credit line limit
    if (newBalance.compareTo(this.creditLine.multiply(BigDecimal.valueOf(-1))) < 0) {
      log.warn("Credit line exceeded, transaction is not allowed. Account : {}, Transaction : {}", accountId,
          command.getTransactionId());
      AggregateLifecycle.apply(
          new AmountDebitPendingEvent(
              command.getTransactionId(),
              command.getAccountId(),
              command.getAmount(),
              "PENDING",
              "Credit line exceeded"));
    } else {
      AggregateLifecycle.apply(
          new AmountDebitedEvent(
              command.getTransactionId(),
              command.getAccountId(),
              command.getAmount(),
              // new balance is sent in the event
              newBalance));
    }
  }

  @EventSourcingHandler
  public void on(AmountDebitedEvent event) {
    this.balance = event.getNewBalance();
  }

  @EventSourcingHandler
  public void on(AmountDebitPendingEvent event) {
    // Nothing changes here
    // New balance is not assigned to the balance value
  }

  @CommandHandler
  public void handle(CreditAmountCommand command) {
    AggregateLifecycle.apply(
        new AmountCreditedEvent(
            command.getTransactionId(),
            command.getAccountId(),
            command.getAmount(),
            // new balance is sent in the event
            this.balance.add(command.getAmount())));
  }

  @EventSourcingHandler
  public void on(AmountCreditedEvent event) {
    this.balance = event.getNewBalance();
  }
}
