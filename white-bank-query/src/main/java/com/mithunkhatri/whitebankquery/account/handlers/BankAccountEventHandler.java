package com.mithunkhatri.whitebankquery.account.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.mithunkhatri.whitebankcommon.account.events.AmountCreditedEvent;
import com.mithunkhatri.whitebankcommon.account.events.AmountDebitedEvent;
import com.mithunkhatri.whitebankcommon.account.events.BankAccountCreatedEvent;
import com.mithunkhatri.whitebankquery.account.models.AccountTransaction;
import com.mithunkhatri.whitebankquery.account.models.BankAccount;
import com.mithunkhatri.whitebankquery.account.repositories.BankAccountRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BankAccountEventHandler {
    
    private BankAccountRepository bankAccountRepository;

    @EventHandler
    public void handle(BankAccountCreatedEvent event) {
        this.bankAccountRepository
            .save(new BankAccount(
                event.getAccountId(),
                event.getAccountOwner(),
                event.getInitialDeposit(),
                event.getOverdraftLimit(),
                "ACTIVE",
                new ArrayList<>()
            ));
    }

    @EventHandler
    public void handle(AmountDebitedEvent event) {
        Optional<BankAccount> accountOptional = this.bankAccountRepository.findById(event.getAccountId());
        if(!accountOptional.isPresent()) {
            throw new RuntimeException("Account does not exist with id : " + event.getAccountId());
        }
        BankAccount account = accountOptional.get();
        account.getTransactions()
            .add(new AccountTransaction(new Date(), "DEBIT", event.getAmount()));
        account.setBalance(event.getNewBalance());
        this.bankAccountRepository.save(account);
    }

    @EventHandler
    public void handle(AmountCreditedEvent event) {
        Optional<BankAccount> accountOptional = this.bankAccountRepository.findById(event.getAccountId());
        if(!accountOptional.isPresent()) {
            throw new RuntimeException("Account does not exist with id : " + event.getAccountId());
        }
        BankAccount account = accountOptional.get();
        account.getTransactions()
            .add(new AccountTransaction(new Date(), "CREDIT", event.getAmount()));
        account.setBalance(event.getNewBalance());
        this.bankAccountRepository.save(account);
    }
}
