package com.mithunkhatri.whitebankquery.account.controllers;

import static com.mithunkhatri.whitebankquery.account.mappers.BankAccountMapper.docToResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mithunkhatri.whitebankquery.account.services.BankAccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/accounts")
@AllArgsConstructor
public class BankAccountController {

    private BankAccountService bankAccountService;

    @GetMapping
    public List<BankAccountResponse> all() {
        return this.bankAccountService.getAll()
                .stream()
                .map(bankAccount -> docToResponse(bankAccount))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/red")
    public List<BankAccountResponse> red() {
        return null;
    }

    @GetMapping(value = "/{accountId}")
    public BankAccountResponse account(@PathVariable String accountId) {
        return docToResponse(this.bankAccountService.getAccountById(accountId));
    }

    @GetMapping(value = "/{accountId}/balance")
    public BankAccountBalanceResponse balance(@PathVariable String accountId) {
        return null;
    }

    @GetMapping(value = "/{accountId}/transactions")
    public List<AccountTransactionResponse> accountTransactions(@PathVariable String accountId) {
        return null;
    }
}
