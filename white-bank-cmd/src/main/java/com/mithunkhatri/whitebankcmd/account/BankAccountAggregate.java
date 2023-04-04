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
import com.mithunkhatri.whitebankcommon.account.events.AmountDebitedEvent;
import com.mithunkhatri.whitebankcommon.account.events.BankAccountCreatedEvent;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class BankAccountAggregate {
    
    @AggregateIdentifier
    private String accountId;

    private String accountOwner;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;
    private String status;

    @CommandHandler
    public BankAccountAggregate(CreateBankAccountCommand command) {
        AggregateLifecycle.apply(
            new BankAccountCreatedEvent(
                command.getAccountId(),
                command.getAccountOwner(),
                command.getInitialDeposit(),
                command.getOverdraftLimit()
            )
        );
    }

    @EventSourcingHandler
    public void on(BankAccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.accountOwner = event.getAccountOwner();
        this.balance = event.getInitialDeposit();
        this.overdraftLimit = event.getOverdraftLimit();
        this.status = "ACTIVE"; // Assuming the account to be active right after its created
    }

    @CommandHandler
    public void handle(DebitAmountCommand command) {
        AggregateLifecycle.apply(
            new AmountDebitedEvent(command.getAccountId(), 
                command.getAmount(),
                this.balance.subtract(command.getAmount()))
        );
    }

    @EventSourcingHandler
    public void on(AmountDebitedEvent event) {
        // not considering overdraft limit check
        this.balance = event.getNewBalance();
    }

    @CommandHandler
    public void handle(CreditAmountCommand command) {
        AggregateLifecycle.apply(
            new AmountCreditedEvent(command.getAccountId(), 
                command.getAmount(),
                this.balance.add(command.getAmount()))
        );
    }

    @EventSourcingHandler
    public void on(AmountCreditedEvent event) {
        this.balance = event.getNewBalance();
    }
}
