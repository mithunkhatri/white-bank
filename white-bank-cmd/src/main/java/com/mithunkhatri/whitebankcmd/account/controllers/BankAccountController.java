package com.mithunkhatri.whitebankcmd.account.controllers;

import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mithunkhatri.whitebankcmd.account.services.BankAccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/accounts")
@AllArgsConstructor
public class BankAccountController {

    private BankAccountService bankAccountService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompletableFuture<BankAccountResponse> createBankAccount(@Valid @RequestBody BankAccountRequest request) {
        return bankAccountService.createBankAccount(request);
    }

    @PutMapping(value = "/{accountId}/debit")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void debit(@Valid @RequestBody PaymentRequest request, @PathVariable String accountId) {
        this.bankAccountService.debitAmount(request, accountId);
    }

    @PutMapping(value = "/{accountId}/credit")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void credit(@Valid @RequestBody PaymentRequest request, @PathVariable String accountId) {
        this.bankAccountService.creditAmount(request, accountId);
    }
}
