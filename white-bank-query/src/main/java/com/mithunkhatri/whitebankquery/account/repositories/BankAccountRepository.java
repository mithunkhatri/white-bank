package com.mithunkhatri.whitebankquery.account.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mithunkhatri.whitebankquery.account.models.BankAccount;

public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
    
}
