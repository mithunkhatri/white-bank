package com.mithunkhatri.whitebankquery.account.handlers;

import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.mithunkhatri.whitebankquery.account.models.BankAccount;
import com.mithunkhatri.whitebankquery.account.queries.FindAccountById;
import com.mithunkhatri.whitebankquery.account.queries.FindAllAccount;
import com.mithunkhatri.whitebankquery.account.repositories.BankAccountRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BankAccountQueryHandler {
    
    private BankAccountRepository bankAccountRepository;

    @QueryHandler
    public List<BankAccount> handle(FindAllAccount query) {
        return this.bankAccountRepository.findAll();
    }

    @QueryHandler
    public Optional<BankAccount> handle(FindAccountById query) {
        return this.bankAccountRepository.findById(query.getAccountId());
    }
}
