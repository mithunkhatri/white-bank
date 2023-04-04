package com.mithunkhatri.whitebankquery.account.services;

import java.util.List;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

import com.mithunkhatri.whitebankquery.account.models.BankAccount;
import com.mithunkhatri.whitebankquery.account.queries.FindAccountById;
import com.mithunkhatri.whitebankquery.account.queries.FindAllAccount;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BankAccountService {
    
    private QueryGateway queryGateway;

    public List<BankAccount> getAll() {
        return this.queryGateway.query(new FindAllAccount(), List.class).join();
    }

    public BankAccount getAccountById(String accountId) {
        return this.queryGateway.query(new FindAccountById(accountId), BankAccount.class).join();
    }
}
