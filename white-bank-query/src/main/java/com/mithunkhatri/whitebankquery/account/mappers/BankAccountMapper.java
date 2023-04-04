package com.mithunkhatri.whitebankquery.account.mappers;

import com.mithunkhatri.whitebankquery.account.controllers.BankAccountResponse;
import com.mithunkhatri.whitebankquery.account.models.BankAccount;

public class BankAccountMapper {
    
    public static BankAccountResponse docToResponse(BankAccount bankAccount) {
        return new BankAccountResponse(
            bankAccount.getAccountId(),
            bankAccount.getAccountOwner(),
            bankAccount.getBalance(),
            bankAccount.getOverdraftLimit(),
            bankAccount.getStatus());
    }
}
