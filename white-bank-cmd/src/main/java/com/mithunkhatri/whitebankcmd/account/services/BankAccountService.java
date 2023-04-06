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

/**
 * Bank account command gateway service
 */
@Component
@AllArgsConstructor
public class BankAccountService {

  private CommandGateway commandGateway;

  /**
   * Sends create bank account command
   */
  public CompletableFuture<BankAccountResponse> createBankAccount(BankAccountRequest request) {
    return commandGateway.send(new CreateBankAccountCommand(
        UUID.randomUUID().toString(),
        request.getAccountOwner(),
        request.getInitialDeposit(),
        request.getCreditLine()));
  }

  /**
   * Sends amount debit command
   */
  public void debitAmount(PaymentRequest request, String accountId) {
    this.commandGateway.send(
        new DebitAmountCommand(accountId, UUID.randomUUID().toString(), request.getAmount()));
  }

  /**
   * Sends amount credit command
   */
  public void creditAmount(PaymentRequest request, String accountId) {
    this.commandGateway.send(
        new CreditAmountCommand(accountId, UUID.randomUUID().toString(), request.getAmount()));
  }
}
