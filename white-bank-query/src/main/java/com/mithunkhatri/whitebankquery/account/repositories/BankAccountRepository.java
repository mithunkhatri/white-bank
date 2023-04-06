package com.mithunkhatri.whitebankquery.account.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mithunkhatri.whitebankquery.account.models.BankAccount;

/**
 * Mongo repository for bank account document
 */
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
    
    List<BankAccount> findByBalanceLessThan(BigDecimal redMark);
}
