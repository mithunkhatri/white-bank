package com.mithunkhatri.whitebankcmd.account.services;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

import com.mithunkhatri.whitebankcmd.account.commands.CreateBankAccountCommand;
import com.mithunkhatri.whitebankcmd.account.commands.CreditAmountCommand;
import com.mithunkhatri.whitebankcmd.account.commands.DebitAmountCommand;
import com.mithunkhatri.whitebankcmd.account.controllers.BankAccountRequest;
import com.mithunkhatri.whitebankcmd.account.controllers.BankAccountResponse;
import com.mithunkhatri.whitebankcmd.account.controllers.PaymentRequest;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BankAccountService {
    
    private CommandGateway commandGateway;

    public CompletableFuture<BankAccountResponse> createBankAccount(BankAccountRequest request) {
        return commandGateway.send(new CreateBankAccountCommand(
            UUID.randomUUID().toString(),
            request.getAccountOwner(),
            request.getInitialDeposit(),
            request.getCreditLine()
        ));
    } 

    public void debitAmount(PaymentRequest request, String accountId) {
        if(BigDecimal.ZERO.equals(request.getAmount())) {
            throw new RuntimeException("Debit amount can't be zero");
        }
        this.commandGateway.send(
            new DebitAmountCommand(accountId, UUID.randomUUID().toString(), request.getAmount())
        );
    }

    public void creditAmount(PaymentRequest request, String accountId) {
        if(BigDecimal.ZERO.equals(request.getAmount())) {
            throw new RuntimeException("Credit amount can't be zero");
        }
        this.commandGateway.send(
            new CreditAmountCommand(accountId, UUID.randomUUID().toString(), request.getAmount())
        );
    }
}
