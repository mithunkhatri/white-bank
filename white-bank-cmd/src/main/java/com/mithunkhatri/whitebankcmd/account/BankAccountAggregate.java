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

@Aggregate
@NoArgsConstructor
@Slf4j
public class BankAccountAggregate {

  @AggregateIdentifier
  private String accountId;

  private String accountOwner;
  private BigDecimal balance;
  private BigDecimal creditLine;
  private String status;

  @CommandHandler
  public BankAccountAggregate(CreateBankAccountCommand command) {
    AggregateLifecycle.apply(
        new BankAccountCreatedEvent(
            command.getAccountId(),
            command.getAccountOwner(),
            command.getInitialDeposit(),
            command.getCreditLine()));
  }

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
    BigDecimal newBalance = this.balance.subtract(command.getAmount());
    if (newBalance.compareTo(this.creditLine.multiply(BigDecimal.valueOf(-1))) < 0) {
      log.warn("Credit line exceeded, transaction is not allowed. Account : {}, Transaction : {}", accountId, command.getTransactionId());
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
              newBalance));
    }
  }

  @EventSourcingHandler
  public void on(AmountDebitedEvent event) {
    // not considering overdraft limit check
    this.balance = event.getNewBalance();
  }

  @EventSourcingHandler
  public void on(AmountDebitPendingEvent event) {
    // Nothing changes here
  }

  @CommandHandler
  public void handle(CreditAmountCommand command) {
    AggregateLifecycle.apply(
        new AmountCreditedEvent(
            command.getTransactionId(),
            command.getAccountId(),
            command.getAmount(),
            this.balance.add(command.getAmount())));
  }

  @EventSourcingHandler
  public void on(AmountCreditedEvent event) {
    this.balance = event.getNewBalance();
  }
}
